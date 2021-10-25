package bank.application;

import bank.application.types.Result;
import bank.types.AccountNumber;
import bank.types.UserId;
import java.math.BigDecimal;

/**
 * @author ZhengHao Lou
 * Date    2021/10/24
 */
public interface TransferService {
    // Immediately
    Result<Boolean> transfer(UserId sourceUserId, AccountNumber targetAccountNumber, BigDecimal targetAmount,
                             String targetCurrency);

    // Delay
    Result<Boolean> transferDelayAt(UserId sourceUserId, AccountNumber targetAccountNumber, BigDecimal targetAmount,
                             String targetCurrency, long timestamp);

    // 触发延迟执行的转账
    Result<Boolean> triggerDelayCommand(long nowInMillis);
}

