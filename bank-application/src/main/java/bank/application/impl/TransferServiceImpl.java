package bank.application.impl;

import bank.application.TransferService;
import bank.application.types.Result;
import bank.domain.entity.Account;
import bank.domain.entity.Command;
import bank.domain.external.ExchangeRateService;
import bank.domain.external.IdGeneratorService;
import bank.domain.messaging.AuditMessageProducer;
import bank.domain.messaging.CrossBankTransferMessageProducer;
import bank.domain.repository.AccountRepository;
import bank.domain.repository.CommandRepository;
import bank.domain.repository.TransactionManager;
import bank.domain.service.AccountTransferService;
import bank.domain.service.CommandService;
import bank.domain.service.impl.AccountTransferServiceImpl;
import bank.domain.service.impl.CommandServiceImpl;
import bank.domain.types.AuditMessage;
import bank.domain.types.CrossBankReqMessage;
import bank.types.AccountNumber;
import bank.types.Currency;
import bank.types.ExchangeRate;
import bank.types.Money;
import bank.types.UserId;
import bank.types.command.CommandStatus;
import bank.types.command.OpType;
import bank.types.command.TransferArgu;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Setter;

/**
 * @author ZhengHao Lou
 * Date    2021/10/24
 *
 * Application service
 */
public class TransferServiceImpl implements TransferService {

    // ---------------------------------------------------------------
    // Domain service
    private AccountTransferService accountTransferService = new AccountTransferServiceImpl();

    private CommandService commandService = new CommandServiceImpl();

    // ---------------------------------------------------------------
    // Repository
    @Setter
    private CommandRepository commandRepository;

    @Setter
    private AccountRepository accountRepository;

    @Setter
    private TransactionManager transactionManager;

    // ---------------------------------------------------------------
    // Infrastructure service
    @Setter
    private ExchangeRateService exchangeRateService;

    @Setter
    private IdGeneratorService idGeneratorService;

    @Setter
    private AuditMessageProducer auditMessageProducer;

    @Setter
    private CrossBankTransferMessageProducer crossBankTransferMessageProducer;

    @Override
    public Result<Boolean> transfer(UserId sourceUserId, AccountNumber targetAccountNumber, BigDecimal targetAmount,
                           String targetCurrency) {
        // 1) 参数校验，准备业务逻辑的所需要的入参
        final Money transferMoney = new Money(targetAmount, Currency.valueOf(targetCurrency));

        Account sourceAccount = accountRepository.find(sourceUserId);
        final ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(sourceAccount.getCurrency(),
            transferMoney.getCurrency());
        Account targetAccount = accountRepository.find(targetAccountNumber);

        // 2) 业务逻辑，包含数据的查询、更新，运行在事务中，需要保证绝对一致性
        accountTransferService.transfer(sourceAccount, targetAccount, transferMoney, exchangeRate);
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        // 3) 收尾，e.g. 发送审计消息
        AuditMessage message = new AuditMessage(sourceUserId, sourceAccount.getAccountNumber(),
            targetAccount.getAccountNumber(), transferMoney, new Date());
        auditMessageProducer.send(message);

        return Result.ok();
    }

    @Override
    public Result<Boolean> transferDelayAt(UserId sourceUserId, AccountNumber targetAccountNumber,
                                           BigDecimal targetAmount, String targetCurrency, long delayAt) {
        // 1) 生成一条命令
        TransferArgu argu = new TransferArgu(sourceUserId, targetAccountNumber, targetAmount, targetCurrency);
        long commandId = idGeneratorService.id();
        Command command = new Command(commandId, OpType.TRANSFER, argu, CommandStatus.PENDING);
        commandRepository.save(command);

        // 2) 业务逻辑，添加到延迟队列中
        commandService.addDelayAt(command, delayAt);

        return Result.ok();
    }

    @Override
    public Result<String> cancelDelayedTransfer(UserId sourceUserId, Long commandId) {
        // 1) 校验参数
        Account account = accountRepository.find(sourceUserId);
        Command command = commandRepository.find(account.getAccountNumber(), commandId);
        if (command == null) {
            return Result.failed("Can not find record.");
        }
        if (command.closed()) {
            return Result.failed("Command has been closed.");
        }

        // 2) 修改command的状态
        command.setStatus(CommandStatus.CANCELED);
        commandRepository.save(command);

        // 3) 从timer中删除command
        commandService.del(commandId);
        return Result.ok();
    }

    // 系统外部通过该方法驱动触发执行延迟转账, e.g. 在另外的线程中通过传入时间来驱动
    @Override
    public Result<Boolean> triggerDelayedCommand(long nowInMillis) {
        // 1) 取出可以执行的command
        List<Command> triggeredCommands = commandService.schedule(nowInMillis);

        // 2) 依次执行command
        for (Command command : triggeredCommands) {
            switch (command.getOpType()) {
                case TRANSFER:
                default:
                    this.doDelayedTransfer(command);
            }
        }
        return Result.ok();
    }

    @Override
    public Result<Boolean> transferInterBank(UserId sourceUserId, AccountNumber targetAccountNumber,
                                             BigDecimal targetAmount, String targetCurrency, String targetBank) {
        // 1) 参数校验，准备业务逻辑的所需要的入参
        final Money transferMoney = new Money(targetAmount, Currency.valueOf(targetCurrency));
        Account sourceAccount = accountRepository.find(sourceUserId);
        Account targetAccount = accountRepository.find(targetAccountNumber);

        // 2) 发送跨行转账消息
        Long transactionId = idGeneratorService.id();
        CrossBankReqMessage message = new CrossBankReqMessage(transactionId, targetBank, sourceAccount.getAccountNumber(),
            targetAccount.getAccountNumber(), transferMoney, new Date());
        crossBankTransferMessageProducer.send(message);

        return Result.ok();
    }

    @Override
    public Result<Boolean> handleCrossBankTransferResult(Long transactionId, AccountNumber source,
                                                         BigDecimal targetAmount, Boolean result) {
        return null;
    }

    private void doDelayedTransfer(Command command) {
        // 1) 判断命令是否有效
        Command cmdInDatabase = commandRepository.find(command.getCommandId());
        if (cmdInDatabase.getStatus() != CommandStatus.PENDING) {
            return;
        }

        // 2) 执行转账
        TransferArgu argu = (TransferArgu) command.getArgument();
        Result<Boolean> result = this.transfer(argu.getSourceUserId(), argu.getTargetAccountNumber(), argu.getTargetAmount(),
            argu.getTargetCurrency());

        // 3) 保存转账结果
        if (result.isOk()) {
            cmdInDatabase.setStatus(CommandStatus.SUCCESS);
        } else {
            cmdInDatabase.setStatus(CommandStatus.FAILED);
        }
        commandRepository.save(command);
    }

}
