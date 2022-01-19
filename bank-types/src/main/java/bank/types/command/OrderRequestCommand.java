package bank.types.command;

import bank.types.EventType;
import bank.types.OrderAction;
import bank.types.OrderType;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZhengHao Lou
 * Date    2022/01/13
 *
 * 订单下单消息
 */
@Data
@NoArgsConstructor
public class OrderRequestCommand {
    private long correlationId;
    private long orderId;
    private long userId;
    private long accountId;
    private EventType eventType;
    private OrderType orderType;
    private OrderAction orderAction;
    private BigDecimal limitPrice;
    private String symbol;
    private int totalQuantity;
}
