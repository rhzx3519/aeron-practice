package bank.persistence.repo;

import bank.domain.entity.Account;
import bank.domain.repository.AccountRepository;
import bank.persistence.dao.AccountBuilder;
import bank.persistence.dao.AccountDO;
import bank.persistence.dao.AccountDao;
import bank.types.AccountId;
import bank.types.AccountNumber;
import bank.types.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 *
 * Account
 */
@Service
public class AccountRepositoryImpl implements AccountRepository {

    @Autowired
    private AccountDao accountDAO;

    @Autowired
    private AccountBuilder accountBuilder;

    @Override
    public Account find(AccountId id) {
        AccountDO accountDO = accountDAO.selectById(id.getValue());
        return accountBuilder.toAccount(accountDO);
    }

    @Override
    public Account find(AccountNumber accountNumber) {
        AccountDO accountDO = accountDAO.selectByAccountNumber(accountNumber.getValue());
        return accountBuilder.toAccount(accountDO);
    }

    @Override
    public Account find(UserId userId) {
        AccountDO accountDO = accountDAO.selectByUserId(userId.getValue());
        return accountBuilder.toAccount(accountDO);
    }

    @Override
    public Account save(Account account) {
        AccountDO accountDO = accountBuilder.fromAccount(account);
        if (accountDO.getId() == null) {
            accountDAO.insert(accountDO);
        } else {
            accountDAO.update(accountDO);
        }
        return accountBuilder.toAccount(accountDO);
    }
}
