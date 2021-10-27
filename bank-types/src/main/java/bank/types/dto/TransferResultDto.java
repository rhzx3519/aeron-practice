package bank.types.dto;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @author ZhengHao Lou
 * Date    2021/10/27
 */
@Data
public class TransferResultDto {
    private Boolean result;
    private String accountNumber;
    private BigDecimal amount;
    private String currency;
}
