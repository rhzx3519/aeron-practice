package bank.domain.external;

import bank.types.Currency;
import bank.types.ExchangeRate;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
public interface ExchangeRateService {
    ExchangeRate getExchangeRate(Currency source, Currency target);
}
