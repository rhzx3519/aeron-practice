package bank.domain.service;

import bank.domain.entity.Order;
import bank.domain.entity.OrderBook;
import bank.domain.types.Quote;
import java.util.List;

/**
 * @author ZhengHao Lou
 * Date    2022/01/13
 */
public interface OrderTriggerService {

    void setOrderBook(OrderBook orderBook);

    List<Order> trigger(Quote quote);
}
