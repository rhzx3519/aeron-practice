package bank.types.command;

import bank.types.command.TransferCommand;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * @author ZhengHao Lou
 * Date    2021/10/27
 */
@Data
@Builder
public class TransferDelayAtCommand {
    @NonNull
    private TransferCommand transferCommand;
    @NonNull
    private Long timestamp;
}
