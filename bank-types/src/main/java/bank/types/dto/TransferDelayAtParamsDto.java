package bank.types.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * @author ZhengHao Lou
 * Date    2021/10/27
 */
@Data
@Builder
public class TransferDelayAtParamsDto {
    @NonNull
    private TransferParamsDto transferParams;
    @NonNull
    private Long timestamp;
}
