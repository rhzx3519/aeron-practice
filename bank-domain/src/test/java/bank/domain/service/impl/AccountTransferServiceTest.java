package bank.domain.service.impl;

import bank.domain.entity.Account;
import bank.types.AccountId;
import bank.types.AccountNumber;
import bank.types.Currency;
import bank.types.ExchangeRate;
import bank.types.Money;
import bank.types.UserId;
import java.math.BigDecimal;
import org.junit.Test;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
public class AccountTransferServiceTest {

    private final AccountTransferServiceImpl accountTransferService = new AccountTransferServiceImpl();

    @Test
    public void testTransfer1() {
        Account sourceAccount = new Account(Currency.CNY, new AccountNumber("001"), new AccountId(),
            new UserId(1), new Money(BigDecimal.valueOf(10), Currency.CNY), new Money(BigDecimal.valueOf(99999), Currency.CNY));
        Account targetAccount = new Account(Currency.USD, new AccountNumber("002"), new AccountId(),
            new UserId(2), new Money(BigDecimal.valueOf(0), Currency.USD), new Money(BigDecimal.valueOf(99999), Currency.USD));

        Money targetMoney = new Money(BigDecimal.ONE, Currency.USD);

        ExchangeRate exchangeRate = new ExchangeRate(Currency.CNY, Currency.USD, BigDecimal.valueOf(0.5));

        accountTransferService.transfer(sourceAccount, targetAccount, targetMoney, exchangeRate);

        assert sourceAccount.getAvailable().getAmount().compareTo(BigDecimal.valueOf(8)) == 0;
        assert targetAccount.getAvailable().getAmount().compareTo(BigDecimal.valueOf(1)) == 0;
    }
}
