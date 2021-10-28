package bank.domain.types;

import bank.domain.entity.Event;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
@Data
public class Timer {

    @AllArgsConstructor
    private static class Item {
        Event command;
        long delayAt;
    }

    private static class TimeTable {
        List<Item> items = new ArrayList<>();

        void add(Event command, long delayAt) {
            items.add(new Item(command, delayAt));
        }

        int del(long commandId) {
            int deleted = 0;
            Iterator<Item> iterator = items.iterator();
            for (; iterator.hasNext();) {
                Item item = iterator.next();
                if (item.command.getEventId() == commandId) {
                    deleted++;
                    iterator.remove();
                }
            }
            return deleted;
        }
    }

    private TimeTable timeTable = new TimeTable();

    public void addDelayAt(Event command, long delayAt) {
        timeTable.add(command, delayAt);
    }

    public int del(long commandId) {
        return timeTable.del(commandId);
    }

    public List<Event> schedule(long timestamp) {
        return Collections.emptyList();
    }
}
