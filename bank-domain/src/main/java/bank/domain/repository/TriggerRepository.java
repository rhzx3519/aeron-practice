package bank.domain.repository;

import bank.domain.entity.Event;
import bank.types.AccountNumber;

/**
 * @author ZhengHao Lou
 * Date    2021/10/26
 */
public interface TriggerRepository {
    Event find(Long commandId);
    Event save(Event command);
    Event find(AccountNumber accountNumber, Long commandId);
}
