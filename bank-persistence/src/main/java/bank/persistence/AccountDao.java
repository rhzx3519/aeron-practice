package bank.persistence;

import org.springframework.stereotype.Service;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 *
 * 返回数据库表对象(data object)
 */
@Service
public class AccountDao {

    public bank.persistence.AccountDO selectById(long id) {
        return null;
    }

    public bank.persistence.AccountDO selectByAccountNumber(String accountNumber) {
        return null;
    }

    public bank.persistence.AccountDO selectByUserId(long userId) {
        return null;
    }

    public boolean insert(bank.persistence.AccountDO accountDO) {
        return false;
    }

    public boolean update(bank.persistence.AccountDO accountDO) {
        return false;
    }
}
