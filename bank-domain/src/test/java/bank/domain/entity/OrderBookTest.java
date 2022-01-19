package bank.domain.entity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import bank.types.OrderAction;
import bank.types.OrderType;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.agrona.ExpandableDirectByteBuffer;
import org.junit.Test;

/**
 * @author ZhengHao Lou
 * Date    2022/01/13
 */
@Slf4j
public class OrderBookTest {

    private final long DEFAULT_ORDER_ID = 5837984578943L;
    private final long DEFAULT_USER_ID = 8329473284928L;
    private final long DEFAULT_ACCOUNT_ID = 8329473284928L;
    private final String DEFAULT_SYMBOL = "TSLA";

    @Test
    public void testSnapshot() {
        final int N = 10;
        OrderBook orderBook = new OrderBook();
        for (int i = 1; i <= N; i++) {
            orderBook.add(newOrder(i));
        }
        assertThat(orderBook.size()).isEqualTo(N);
        ExpandableDirectByteBuffer buffer = new ExpandableDirectByteBuffer();
        int count = orderBook.snapshot(buffer, 0);
        assertThat(count).isGreaterThan(0);

        orderBook.clear();
        orderBook.loadSnapshot(buffer, 0, 0);
        assertThat(orderBook.size()).isEqualTo(N);
    }

    private Order newOrder(long orderId) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setUserId(DEFAULT_USER_ID);
        order.setAccountId(DEFAULT_ACCOUNT_ID);
        order.setOrderType(OrderType.LIMIT);
        order.setOrderAction(OrderAction.BUY);
        order.setLimitPrice(BigDecimal.valueOf(5.9));
        order.setSymbol(DEFAULT_SYMBOL);
        return order;
    }
}
