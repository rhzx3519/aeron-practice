package bank.types;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ZhengHao Lou
 * Date    2021/10/24
 *
 * Domain primitive, 值对象
 */
@Data
@AllArgsConstructor
public class ExchangeRate {
    private Currency source;
    private Currency target;
    private BigDecimal rate;

    /**
     * 根据汇率将source money 转换为 target money,
     * e.g. CNY -> USD
     * @param source
     * @return
     */
    public Money exchangeTo(Money source) {
        return null;
    }
}
