package bank.external.impl;

import bank.domain.external.ExchangeRateService;
import bank.types.Currency;
import bank.types.ExchangeRate;
import bank.external.exchangerate.YahooForexService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Autowired
    private YahooForexService yahooForexService;

    @Override
    public ExchangeRate getExchangeRate(Currency source, Currency target) {
        BigDecimal rate = yahooForexService.getExchangeRate(source.name(), target.name());
        return new ExchangeRate(source, target, rate);
    }
}
