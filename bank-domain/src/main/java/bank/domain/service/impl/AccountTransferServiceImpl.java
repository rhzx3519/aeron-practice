package bank.domain.service.impl;

import bank.domain.entity.Account;
import bank.domain.service.AccountTransferService;
import bank.types.ExchangeRate;
import bank.types.Money;
import org.springframework.stereotype.Service;

@Service
/**
 * @author ZhengHao Lou
 * Date    2021/10/24
 *
 * Domain service
 */
public class AccountTransferServiceImpl implements AccountTransferService {

    /**
     * Absolute consistency
     * @param sourceAccount
     * @param targetAccount
     * @param targetMoney
     * @param exchangeRate
     */
    @Override
    public void transfer(Account sourceAccount, Account targetAccount, Money targetMoney, ExchangeRate exchangeRate) {
        Money sourceMoney = exchangeRate.exchangeTo(sourceAccount.getAvailable());
        assert sourceMoney.compareTo(targetMoney) == 0;

        sourceAccount.withdraw(sourceMoney);
        targetAccount.deposit(targetMoney);
    }
}
