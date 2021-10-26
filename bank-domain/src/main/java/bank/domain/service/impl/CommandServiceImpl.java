package bank.domain.service.impl;

import bank.domain.entity.Command;
import bank.domain.types.Timer;
import bank.domain.service.CommandService;
import java.util.List;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
public class CommandServiceImpl implements CommandService {

    private final Timer timer = new Timer();

    // 尝试触发延迟的command
    @Override
    public List<Command> schedule(long timestamp) {
        List<Command> commands = timer.schedule(timestamp);
        return commands;
    }

    @Override
    public void addDelayAt(Command command, long delayAt) {
        timer.addDelayAt(command, delayAt);
    }

    @Override
    public boolean del(long commandId) {
        return timer.del(commandId) > 0;
    }


}
