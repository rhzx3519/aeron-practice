package bank.domain.entity;

import bank.types.trigger.EventArgs;
import bank.types.trigger.EventStatus;
import bank.types.trigger.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 *
 */
@Data
@AllArgsConstructor
public class Event {
    private Long eventId;
    private OpType opType;
    private EventArgs args;
    private EventStatus status;

    public boolean closed() {
        return status.closed();
    }
}
