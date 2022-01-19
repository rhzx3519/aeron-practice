package bank.application;

import bank.types.command.OrderRequestCommand;
import bank.types.command.QuoteCommand;
import bank.types.event.OrderResponseEvent;
import bank.types.event.TriggerEvent;
import org.agrona.ExpandableDirectByteBuffer;

/**
 * @author ZhengHao Lou
 * Date    2022/01/13
 */
public interface OrderService {

    OrderResponseEvent processOrderRequest(OrderRequestCommand orderRequest);

    TriggerEvent processQuote(QuoteCommand quote);

    /**
     *
     * @param buffer
     * @param offset
     * @return new offset
     */
    int snapshot(ExpandableDirectByteBuffer buffer, int offset);

    String debug();
}
