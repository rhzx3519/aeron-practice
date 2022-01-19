package bank.domain.service.impl;

import bank.domain.entity.Order;
import bank.domain.entity.OrderBook;
import bank.domain.mapper.OrderMapper;
import bank.domain.service.OrderProcessService;
import bank.types.OrderStatus;
import bank.types.command.OrderRequestCommand;
import bank.types.event.OrderResponseEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * Date    2022/01/13
 */
@Slf4j
public class OrderProcessServiceImpl implements OrderProcessService {

    private OrderBook orderBook;

    @Override
    public void setOrderBook(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    @Override
    public OrderResponseEvent process(OrderRequestCommand orderRequest) {
        final long orderId = orderRequest.getOrderId();
        Order order = OrderMapper.INSTANCE.orderRequestCommandToOrder(orderRequest);
        boolean success = false;
        switch (orderRequest.getEventType()) {
            case PLACE:
                success = orderBook.add(order);
                break;
            case REPLACE:
                success = orderBook.update(orderRequest);
                break;
            case CANCEL:
                success = orderBook.cancel(orderRequest.getOrderId());
                break;
            default:
                break;
        }

        OrderResponseEvent response = new OrderResponseEvent();
        response.setCorrelationId(orderRequest.getCorrelationId());
        response.setOrderId(orderRequest.getOrderId());
        response.setUserId(orderRequest.getUserId());
        response.setAccountId(orderRequest.getAccountId());

        if (!success) {
            response.setOrderStatus(OrderStatus.REJECTED);
            return response;
        }
        final Order currentOrder = orderBook.getOrder(orderId);
        response.setOrderStatus(currentOrder.getOrderStatus());
        response.setFilledQuantity(currentOrder.getFilledQuantity());
        response.setTotalQuantity(currentOrder.getTotalQuantity());
        return response;
    }

}
