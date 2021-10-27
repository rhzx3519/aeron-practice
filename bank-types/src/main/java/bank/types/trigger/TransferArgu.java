package bank.types.trigger;

import bank.types.dto.TransferParamsDto;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
@Data
@AllArgsConstructor
public class TransferArgu implements Argument{
    private TransferParamsDto transferParams;
}
