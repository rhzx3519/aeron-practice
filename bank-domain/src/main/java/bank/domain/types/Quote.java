package bank.domain.types;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ZhengHao Lou
 * Date    2022/01/13
 */
@Data
@AllArgsConstructor
public class Quote {
    private String symbol;
    private BigDecimal latestPrice;
    private long timestamp;
}
