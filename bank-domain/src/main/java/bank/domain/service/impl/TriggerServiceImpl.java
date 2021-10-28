package bank.domain.service.impl;

import bank.domain.entity.Event;
import bank.domain.types.Timer;
import bank.domain.service.TriggerService;
import java.util.List;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
public class TriggerServiceImpl implements TriggerService {

    private final Timer timer = new Timer();

    // 尝试触发延迟的command
    @Override
    public List<Event> schedule(long timestamp) {
        List<Event> commands = timer.schedule(timestamp);
        return commands;
    }

    @Override
    public void addDelayAt(Event event, long delayAt) {
        timer.addDelayAt(event, delayAt);
    }

    @Override
    public boolean del(long eventId) {
        return timer.del(eventId) > 0;
    }


}
