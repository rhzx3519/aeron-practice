package bank.domain.entity;

import bank.types.command.Argument;
import bank.types.command.CommandStatus;
import bank.types.command.OpType;
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
