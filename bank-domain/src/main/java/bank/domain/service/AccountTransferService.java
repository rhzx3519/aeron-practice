package bank.domain.service;

import bank.domain.entity.Account;
import bank.types.ExchangeRate;
import bank.types.Money;

/**
 * @author ZhengHao Lou
 * Date    2021/10/24
 */
public interface AccountTransferService {

    void transfer(Account sourceAccount, Account targetAccount, Money targetMoney, ExchangeRate exchangeRate);

}
