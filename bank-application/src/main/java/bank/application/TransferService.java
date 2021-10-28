package bank.application;

import bank.application.types.Result;
import bank.types.AccountNumber;
import bank.types.UserId;
import bank.types.command.TransferDelayAtCommand;
import bank.types.command.TransferCommand;
import bank.types.dto.TransferResultDto;
import java.math.BigDecimal;

/**
 * @author ZhengHao Lou
 * Date    2021/10/24
 */
public interface TransferService {
    // Immediately
    TransferResultDto transfer(TransferCommand transferParams);

    // Delay
    Result<Boolean> transferDelayAt(TransferDelayAtCommand transferDelayAtParams);

    // 撤销延时转账
    Result<String> cancelDelayedTransfer(UserId sourceUserId, Long commandId);

    // 触发延迟执行的转账
    Result<Boolean> triggerDelayedCommand(long nowInMillis);

    // 跨行转账
    Result<Boolean> transferInterBank(UserId sourceUserId, AccountNumber targetAccountNumber, BigDecimal targetAmount,
                                      String targetCurrency, String targetBank);

    // 处理跨行转账结果
    Result<Boolean> handleCrossBankTransferResult(Long transactionId, AccountNumber source, BigDecimal targetAmount, Boolean result);
}

