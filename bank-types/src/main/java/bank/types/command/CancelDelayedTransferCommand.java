package bank.types.command;

import bank.types.UserId;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ZhengHao Lou
 * Date    2021/10/28
 */
@Data
@AllArgsConstructor
public class CancelDelayedTransferCommand {
    private UserId sourceUserId;
    private Long commandId;
}
