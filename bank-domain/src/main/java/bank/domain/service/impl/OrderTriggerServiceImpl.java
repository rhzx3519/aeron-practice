package bank.domain.service.impl;

import bank.domain.entity.Order;
import bank.domain.entity.OrderBook;
import bank.domain.service.OrderTriggerService;
import bank.domain.types.Quote;
import java.util.List;

/**
 * @author ZhengHao Lou
 * Date    2022/01/13
 */
public class OrderTriggerServiceImpl implements OrderTriggerService {

    private OrderBook orderBook;

    @Override
    public void setOrderBook(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    @Override
    public List<Order> trigger(Quote quote) {
        return orderBook.trigger(quote);
    }
}
