package bank.domain.service;

import bank.domain.entity.Event;
import java.util.List;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 * Domain service
 */
public interface TriggerService {

    List<Event> schedule(long timestamp);

    void addDelayAt(Event event, long delayAt);

    boolean del(long eventId);

}
