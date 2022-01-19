package bank.domain.entity;

import bank.generated.order.OrderActionEnum;
import bank.generated.order.OrderDecoder;
import bank.generated.order.OrderEncoder;
import bank.generated.order.OrderTypeEnum;
import bank.types.OrderAction;
import bank.types.OrderStatus;
import bank.types.OrderType;
import bank.types.command.OrderRequestCommand;
import com.sun.org.apache.xpath.internal.operations.Or;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZhengHao Lou
 * Date    2022/01/13
 */
@Data
@NoArgsConstructor
public class Order {
    private long orderId;
    private long userId;
    private long accountId;
    private OrderType orderType;
    private OrderAction orderAction;
    private OrderStatus orderStatus;
    private BigDecimal limitPrice;
    private String symbol;
    private int totalQuantity;
    private int filledQuantity;
    private long createdAt;
    private long expiredAt;

    public static Order build(OrderRequestCommand orderRequest) {
        Order order = new Order();
        order.setOrderId(orderRequest.getOrderId());
        order.setSymbol(orderRequest.getSymbol());
        order.setOrderType(orderRequest.getOrderType());
        order.setOrderAction(orderRequest.getOrderAction());
        order.setAccountId(orderRequest.getAccountId());
        order.setUserId(orderRequest.getUserId());
        order.setLimitPrice(orderRequest.getLimitPrice());
        order.setFilledQuantity(0);
        order.setTotalQuantity(orderRequest.getTotalQuantity());

        return order;
    }

    public void update(OrderRequestCommand orderRequest) {
        setTotalQuantity(orderRequest.getTotalQuantity());
        setLimitPrice(orderRequest.getLimitPrice());
    }

    public void cancel() {
        setOrderStatus(OrderStatus.CANCELED);
    }

    public void extractFrom(OrderDecoder orderDecoder) {
        this.setOrderId(orderDecoder.orderId());
        this.setUserId(orderDecoder.userId());
        this.setAccountId(orderDecoder.accountId());
        this.setOrderAction(OrderAction.valueOf(orderDecoder.orderAction().name()));
        this.setOrderType(OrderType.valueOf(orderDecoder.orderType().name()));
        this.setLimitPrice(BigDecimal.valueOf(orderDecoder.limitPrice()));
        this.setSymbol(orderDecoder.symbol());
    }

    public void extractTo(OrderEncoder orderEncoder) {
        orderEncoder.orderId(this.getOrderId());
        orderEncoder.userId(this.getUserId());
        orderEncoder.accountId(this.getAccountId());
        switch (this.getOrderAction()) {
            case SELL:
                orderEncoder.orderAction(OrderActionEnum.SELL);
                break;
            case BUY:
                orderEncoder.orderAction(OrderActionEnum.BUY);
                break;
            default:
                break;
        }
        switch (this.getOrderType()) {
            case LIMIT:
                orderEncoder.orderType(OrderTypeEnum.LIMIT);
                break;
            case STOP:
                orderEncoder.orderType(OrderTypeEnum.STOP);
                break;
            default:
                break;
        }
        orderEncoder.limitPrice(this.getLimitPrice().doubleValue());
        orderEncoder.symbol(this.getSymbol());
    }
}
