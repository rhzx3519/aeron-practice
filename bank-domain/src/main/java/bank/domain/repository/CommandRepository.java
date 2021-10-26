package bank.domain.repository;

import bank.domain.entity.Command;
import bank.types.AccountNumber;
import java.util.List;

/**
 * @author ZhengHao Lou
 * Date    2021/10/26
 */
public interface CommandRepository {
    Command find(Long commandId);
    Command save(Command command);
    Command find(AccountNumber accountNumber, Long commandId);
}
