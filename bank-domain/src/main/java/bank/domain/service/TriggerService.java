package bank.domain.service;

import bank.domain.entity.Command;
import java.util.List;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 * Domain service
 */
public interface TriggerService {

    List<Command> schedule(long timestamp);

    void addDelayAt(Command command, long delayAt);

    boolean del(long commandId);

}
