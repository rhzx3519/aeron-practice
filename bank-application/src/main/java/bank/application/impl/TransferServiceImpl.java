package bank.application.impl;

import bank.application.TransferService;
import bank.application.types.Result;
import bank.domain.entity.Account;
import bank.domain.external.ExchangeRateService;
import bank.domain.messaging.AuditMessageProducer;
import bank.domain.repository.AccountRepository;
import bank.domain.service.AccountTransferService;
import bank.domain.service.TimerService;
import bank.domain.service.impl.AccountTransferServiceImpl;
import bank.domain.service.impl.TimerServiceImpl;
import bank.domain.types.AuditMessage;
import bank.types.AccountNumber;
import bank.types.Currency;
import bank.types.ExchangeRate;
import bank.types.Money;
import bank.types.UserId;
import bank.types.command.Command;
import bank.types.command.OpType;
import bank.types.command.TransferArgu;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Setter;

/**
 * @author ZhengHao Lou
 * Date    2021/10/24
 */
public class TransferServiceImpl implements TransferService {

    private AccountTransferService accountTransferService = new AccountTransferServiceImpl();

    private TimerService timerService = new TimerServiceImpl();

    @Setter
    private ExchangeRateService exchangeRateService;

    @Setter
    private AccountRepository accountRepository;

    @Setter
    private AuditMessageProducer auditMessageProducer;

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

        return Result.ok(true);
    }

    @Override
    public Result<Boolean> transferDelayAt(UserId sourceUserId, AccountNumber targetAccountNumber,
                                           BigDecimal targetAmount, String targetCurrency, long delayAt) {
        // 1) 参数校验，准备业务逻辑的所需要的入参
        TransferArgu argu = new TransferArgu(sourceUserId, targetAccountNumber, targetAmount, targetCurrency);
        Command command = new Command(OpType.TRANSFER, argu);

        // 2) 业务逻辑，添加到延迟队列中
        timerService.addDelayAt(command, delayAt);

        return Result.ok(true);
    }

    // 系统外部通过改方法驱动触发执行延迟转账, e.g. 在另外的线程中通过传入时间来驱动
    @Override
    public Result<Boolean> triggerDelayedCommand(long nowInMillis) {
        // 1) 取出可以执行的command
        List<Command> triggeredCommands = timerService.schedule(nowInMillis);

        // 2) 依次执行command
        for (Command command : triggeredCommands) {
            switch (command.getOpType()) {
                case TRANSFER:
                default:
                    TransferArgu argu = (TransferArgu) command.getArgument();
                    this.transfer(argu.getSourceUserId(), argu.getTargetAccountNumber(), argu.getTargetAmount(),
                        argu.getTargetCurrency());
                    break;
            }
        }
        return Result.ok(true);
    }

}
