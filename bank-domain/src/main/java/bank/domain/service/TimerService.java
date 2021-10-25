package bank.domain.service;

import bank.types.command.Command;
import java.util.List;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 * Domain service
 */
public interface TimerService {

    List<Command> schedule(long timestamp);

    void addDelayAt(Command command, long delayAt);
}
