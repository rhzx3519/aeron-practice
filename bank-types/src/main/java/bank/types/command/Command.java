package bank.types.command;

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
    private OpType opType;
    private Argument argument;
}
