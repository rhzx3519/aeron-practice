package bank.external.exchangerate;

import java.math.BigDecimal;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
public interface YahooForexService {

    BigDecimal getExchangeRate(String source, String target);
}
