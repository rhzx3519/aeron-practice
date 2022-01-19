package bank.types.event;

import bank.types.OrderStatus;
import lombok.Data;

/**
 * @author ZhengHao Lou
 * Date    2022/01/13
 */
@Data
public class OrderResponseEvent {
    private long correlationId;
    private long orderId;
    private long userId;
    private long accountId;
    private OrderStatus orderStatus;
    private int totalQuantity;
    private int filledQuantity;
}
