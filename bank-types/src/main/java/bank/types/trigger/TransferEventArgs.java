package bank.types.trigger;

import bank.types.command.TransferCommand;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
@Data
@AllArgsConstructor
public class TransferEventArgs implements EventArgs {
    private TransferCommand transferCommand;
}
