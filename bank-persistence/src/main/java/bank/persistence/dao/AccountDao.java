package bank.persistence.dao;

import org.springframework.stereotype.Service;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 *
 * 返回数据库表对象(data object)
 */
@Service
public class AccountDao {

    public AccountDO selectById(long id) {
        return null;
    }

    public AccountDO selectByAccountNumber(String accountNumber) {
        return null;
    }

    public AccountDO selectByUserId(long userId) {
        return null;
    }

    public boolean insert(AccountDO accountDO) {
        return false;
    }

    public boolean update(AccountDO accountDO) {
        return false;
    }
}
