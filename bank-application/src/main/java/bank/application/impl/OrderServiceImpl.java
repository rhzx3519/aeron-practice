package bank.application.impl;

import bank.application.OrderService;
import bank.domain.entity.OrderBook;
import bank.domain.service.OrderProcessService;
import bank.domain.service.OrderTriggerService;
import bank.domain.service.impl.OrderProcessServiceImpl;
import bank.domain.service.impl.OrderTriggerServiceImpl;
import bank.types.command.OrderRequestCommand;
import bank.types.command.QuoteCommand;
import bank.types.event.OrderResponseEvent;
import bank.types.event.TriggerEvent;
import lombok.extern.slf4j.Slf4j;
import org.agrona.ExpandableDirectByteBuffer;

/**
 * @author ZhengHao Lou
 * Date    2022/01/13
 */
@Slf4j
public class OrderServiceImpl implements OrderService {
    // ---------------------------------------------------------------
    // Domain service
    private OrderProcessService orderProcessService = new OrderProcessServiceImpl();

    private OrderTriggerService orderTriggerService = new OrderTriggerServiceImpl();

    private final OrderBook orderBook = new OrderBook();

    public OrderServiceImpl() {
        orderProcessService.setOrderBook(orderBook);
    }

    @Override
    public OrderResponseEvent processOrderRequest(OrderRequestCommand orderRequest) {
        return orderProcessService.process(orderRequest);
    }

    @Override
    public TriggerEvent processQuote(QuoteCommand quote) {
        return null;
    }

    @Override
    public int snapshot(ExpandableDirectByteBuffer buffer, int offset) {
        return orderBook.snapshot(buffer, offset);
    }

    @Override
    public String debug() {
        return orderBook.debug();
    }
}
