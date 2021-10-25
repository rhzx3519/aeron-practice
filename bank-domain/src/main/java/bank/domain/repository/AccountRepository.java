package bank.domain.repository;

import bank.domain.entity.Account;
import bank.types.AccountId;
import bank.types.AccountNumber;
import bank.types.UserId;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
public interface AccountRepository {
    Account find(AccountId id);
    Account find(AccountNumber accountNumber);
    Account find(UserId userId);
    Account save(Account account);
}
