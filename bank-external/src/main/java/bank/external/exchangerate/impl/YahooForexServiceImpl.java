package bank.external.exchangerate.impl;

import bank.external.exchangerate.YahooForexService;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
@Service
public class YahooForexServiceImpl implements YahooForexService {
    @Override
    public BigDecimal getExchangeRate(String source, String target) {
        return BigDecimal.ONE;
    }
}
