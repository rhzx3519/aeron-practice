package bank.domain.entity;

import bank.types.trigger.Argument;
import bank.types.trigger.CommandStatus;
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
public class Command {
    private Long commandId;
    private OpType opType;
    private Argument argument;
    private CommandStatus status;

    public boolean closed() {
        return status.closed();
    }
}
