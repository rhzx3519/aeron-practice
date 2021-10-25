package external.impl;

import bank.domain.external.ExchangeRateService;
import bank.types.Currency;
import bank.types.ExchangeRate;
import org.springframework.stereotype.Service;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Override
    public ExchangeRate getExchangeRate(Currency source, Currency target) {
        return null;
    }
}
