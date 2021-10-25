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

    // 两者币种必须相同
    public int compareTo(Money other) {
        assert this.currency == other.currency;
        return amount.compareTo(other.amount);
    }

    public Money add(Money other) {
        assert this.currency == other.currency;
        this.amount = this.amount.add(other.amount);
        return new Money(this.amount, currency);
    }

    public Money subtract(Money other) {
        assert this.currency == other.currency;
        this.amount = this.amount.subtract(other.amount);
        return new Money(this.amount, currency);
    }
}
