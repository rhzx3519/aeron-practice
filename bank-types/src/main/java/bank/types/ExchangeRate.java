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
    private Currency sourceCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;

    /**
     * 根据汇率将source money 转换为 target money,
     * e.g. CNY -> USD
     * @param source
     * @return
     */
    public Money exchangeTo(Money source) {
        return new Money(source.getAmount().multiply(rate), targetCurrency);
    }

    public Money exchangeFrom(Money target) {
        return new Money(target.getAmount().divide(rate), sourceCurrency);
    }
}
