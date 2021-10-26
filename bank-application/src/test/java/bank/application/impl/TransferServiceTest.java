package bank.application.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import bank.application.ApplicationTestBase;
import bank.domain.entity.Account;
import bank.types.AccountId;
import bank.types.AccountNumber;
import bank.types.Currency;
import bank.types.ExchangeRate;
import bank.types.Money;
import bank.types.UserId;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
public class TransferServiceTest extends ApplicationTestBase {

    private TransferServiceImpl transferService = new TransferServiceImpl();

    @Before
    public void setup() {
        transferService.setExchangeRateService(exchangeRateService);
        transferService.setAccountRepository(accountRepository);
        transferService.setAuditMessageProducer(auditMessageProducer);
    }

    @Test
    public void testTransfer() {
        UserId user1 = new UserId(1);
        AccountNumber accountNumber1 = new AccountNumber("001");
        BigDecimal targetAmount = BigDecimal.ONE;
        String targetCurrency = Currency.USD.name();

        Account sourceAccount = new Account(Currency.CNY, new AccountNumber("001"), new AccountId(),
            new UserId(1), new Money(BigDecimal.valueOf(10), Currency.CNY), new Money(BigDecimal.valueOf(99999), Currency.CNY));
        Account targetAccount = new Account(Currency.USD, new AccountNumber("002"), new AccountId(),
            new UserId(2), new Money(BigDecimal.valueOf(0), Currency.USD), new Money(BigDecimal.valueOf(99999), Currency.USD));

        Money targetMoney = new Money(BigDecimal.ONE, Currency.USD);

        ExchangeRate exchangeRate = new ExchangeRate(Currency.CNY, Currency.USD, BigDecimal.valueOf(0.5));

        doReturn(null).when(accountRepository).save(any());
        doReturn(sourceAccount).when(accountRepository).find(user1);
        doReturn(targetAccount).when(accountRepository).find(accountNumber1);
        doReturn(exchangeRate).when(exchangeRateService).getExchangeRate(Currency.CNY, Currency.USD);

        transferService.transfer(user1, accountNumber1, targetAmount, targetCurrency);

        assert sourceAccount.getAvailable().getAmount().compareTo(BigDecimal.valueOf(8)) == 0;
        assert targetAccount.getAvailable().getAmount().compareTo(BigDecimal.valueOf(1)) == 0;
    }

}
