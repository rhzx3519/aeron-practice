package bank.domain.entity;

import bank.exception.DailyLimitExceededException;
import bank.exception.InsufficientFundsException;
import bank.exception.InvalidCurrencyException;
import bank.types.AccountId;
import bank.types.AccountNumber;
import bank.types.Currency;
import bank.types.Money;
import bank.types.UserId;
import lombok.Data;

/**
 * @author ZhengHao Lou
 * Date    2021/10/24
 */
@Data
public class Account {
    private Currency currency;
    private AccountNumber accountNumber;
    private AccountId id;
    private UserId userId;
    private Money available;
    private Money dailyLimit;

    public Currency getCurrency() {
        return this.available.getCurrency();
    }

    // 转入
    public void deposit(Money money) {
        if (!this.getCurrency().equals(money.getCurrency())) {
            throw new InvalidCurrencyException();
        }
        this.available = this.available.add(money);
    }

    // 转出
    public void withdraw(Money money) {
        if (this.available.compareTo(money) < 0) {
            throw new InsufficientFundsException();
        }
        if (this.dailyLimit.compareTo(money) < 0) {
            throw new DailyLimitExceededException();
        }
        this.available = this.available.subtract(money);
    }
}
