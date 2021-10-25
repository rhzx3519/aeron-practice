package bank.types;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ZhengHao Lou
 * Date    2021/10/24
 */
@Data
@AllArgsConstructor
public class Money {
    private BigDecimal amount;
    private Currency currency;

    public int compareTo(Money other) {
        return 1;
    }

    public Money add(Money other) {
        return null;
    }

    public Money subtract(Money other) {
        return null;
    }
}
