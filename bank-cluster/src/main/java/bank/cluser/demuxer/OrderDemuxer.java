package bank.cluser.demuxer;

import bank.application.OrderService;
import bank.application.impl.OrderServiceImpl;
import bank.generated.order.OrderRequestMessageDecoder;
import bank.generated.order.OrderResponseMessageEncoder;
import bank.generated.order.OrderStatusEnum;
import bank.types.EventType;
import bank.types.OrderAction;
import bank.types.OrderType;
import bank.types.command.OrderRequestCommand;
import bank.types.event.OrderResponseEvent;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.aeron.Publication;
import io.aeron.logbuffer.FragmentHandler;
import io.aeron.logbuffer.Header;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.agrona.DirectBuffer;
import org.agrona.ExpandableDirectByteBuffer;

/**
 * @author ZhengHao Lou
 * Date    2022/01/14
 */
@Slf4j
public class OrderDemuxer extends BaseDemuxer implements FragmentHandler {

    private final OrderService orderService = new OrderServiceImpl();
    private final OrderRequestMessageDecoder orderRequestDecoder = new OrderRequestMessageDecoder();
    private final OrderResponseMessageEncoder orderResponseEncoder = new OrderResponseMessageEncoder();
    private final ExpandableDirectByteBuffer buffer = new ExpandableDirectByteBuffer(1<<10);

    private static final String ORDER_GATEWAY_CHANNEL = "gateways.order.channel";
    private static final String ORDER_GATEWAY_STREAM_ID = "gateways.order.streamId";
    private final String orderGatewayChannel;
    private final Integer orderGatewayStreamId;
    private Publication publication;

    public OrderDemuxer() {
        Config config = ConfigFactory.load();
        orderGatewayChannel = config.getString(ORDER_GATEWAY_CHANNEL);
        orderGatewayStreamId = config.getInt(ORDER_GATEWAY_STREAM_ID);
    }

    @Override
    public void onFragment(DirectBuffer buffer, int offset, int length, Header header) {
        headerDecoder.wrap(buffer, offset);
        final int headerLength = headerDecoder.encodedLength();
        final int blockLength = headerDecoder.blockLength();
        final int version = headerDecoder.version();
        orderRequestDecoder.wrap(buffer, offset + headerLength, blockLength, version);

        OrderRequestCommand request = extractFrom(orderRequestDecoder);
        log.info("Receive requestDecoder: {}, request: {}", orderRequestDecoder, request);
        OrderResponseEvent orderResponse = orderService.processOrderRequest(request);
        log.info("Reply response: {}", orderResponse);

        replyToOrderGateway(orderResponse);
    }

    @Override
    public int takeSnapshot(ExpandableDirectByteBuffer buffer, int offset) {
        return orderService.snapshot(buffer, offset);
    }

    @Override
    public void onTakeLeadership() {
        publication = cluster.aeron().addExclusivePublication(orderGatewayChannel, orderGatewayStreamId);
    }

    @Override
    public void onLoseLeadership() {
        if (publication != null && publication.isConnected()) {
            publication.close();
            publication = null;
        }
    }

    private void replyToOrderGateway(OrderResponseEvent orderResponse) {
        if (!isLeader()) {
            return;
        }
        log.info("reply to client, order response: {}", orderResponse);

        orderResponseEncoder.wrapAndApplyHeader(buffer, 0, headerEncoder);
        orderResponseEncoder.correlationId(orderResponse.getCorrelationId());
        orderResponseEncoder.orderId(orderResponse.getOrderId());
        orderResponseEncoder.userId(orderResponse.getUserId());
        orderResponseEncoder.accountId(orderResponse.getAccountId());
        orderResponseEncoder.orderStatus(OrderStatusEnum.valueOf(orderResponse.getOrderStatus().name()));
        orderResponseEncoder.filledQuantity(orderResponse.getFilledQuantity());
        orderResponseEncoder.totalQuantity(orderResponse.getTotalQuantity());

        publication.offer(buffer, 0, headerEncoder.encodedLength() + orderResponseEncoder.encodedLength());
    }

    private OrderRequestCommand extractFrom(OrderRequestMessageDecoder orderRequestDecoder) {
        OrderRequestCommand orderRequest = new OrderRequestCommand();

        orderRequest.setCorrelationId(orderRequestDecoder.correlationId());
        orderRequest.setOrderId(orderRequestDecoder.orderId());
        orderRequest.setUserId(orderRequestDecoder.userId());
        orderRequest.setAccountId(orderRequestDecoder.accountId());
        orderRequest.setEventType(EventType.valueOf(orderRequestDecoder.eventType().name()));
        orderRequest.setOrderType(OrderType.valueOf(orderRequestDecoder.orderType().name()));
        orderRequest.setOrderAction(OrderAction.valueOf(orderRequestDecoder.orderAction().name()));
        orderRequest.setLimitPrice(BigDecimal.valueOf(orderRequestDecoder.limitPrice()));
        orderRequest.setSymbol(orderRequestDecoder.symbol());
        orderRequest.setTotalQuantity(orderRequestDecoder.totalQuantity());

        return orderRequest;
    }

}
