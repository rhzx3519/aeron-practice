package bank.domain.service;

import bank.domain.entity.OrderBook;
import bank.types.command.OrderRequestCommand;
import bank.types.event.OrderResponseEvent;

/**
 * @author ZhengHao Lou
 * Date    2022/01/13
 */
public interface OrderProcessService {

    void setOrderBook(OrderBook orderBook);

    OrderResponseEvent process(OrderRequestCommand orderRequest);
}
