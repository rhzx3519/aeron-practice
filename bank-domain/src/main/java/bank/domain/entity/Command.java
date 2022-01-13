package bank.domain.entity;

import bank.types.CommandStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ZhengHao Lou
 * Date    2021/11/06
 */
@Data
@AllArgsConstructor
public class Command {
    private long commandId;
    private long parentCmdId;
    private CommandStatus status;
}
