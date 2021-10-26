package bank.domain.repository;

import bank.domain.entity.Command;

/**
 * @author ZhengHao Lou
 * Date    2021/10/26
 */
public interface CommandRepository {
    Command find(Long commandId);
    Command save(Command command);
}
