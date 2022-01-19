package bank.domain.entity;

import bank.domain.types.Quote;
import bank.generated.order.MessageHeaderDecoder;
import bank.generated.order.MessageHeaderEncoder;
import bank.generated.order.OrderDecoder;
import bank.generated.order.OrderEncoder;
import bank.generated.order.OrdersRepoDecoder;
import bank.generated.order.OrdersRepoEncoder;
import bank.types.OrderStatus;
import bank.types.command.OrderRequestCommand;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.agrona.ExpandableDirectByteBuffer;
import org.agrona.collections.Long2ObjectHashMap;

/**
 * @author ZhengHao Lou
 * Date    2022/01/13
 */
public class OrderBook implements Snapshotable {

    private Long2ObjectHashMap<Order> book = new Long2ObjectHashMap<>();

    public boolean add(Order order) {
        order.setOrderStatus(OrderStatus.NEW);
        this.book.put(order.getOrderId(), order);
        return true;
    }

    public boolean update(OrderRequestCommand orderRequest) {
        final long orderId = orderRequest.getOrderId();
        if (!book.containsKey(orderId)) {
            return false;
        }
        Order order = book.get(orderId);
        order.update(orderRequest);
        order.setOrderStatus(OrderStatus.REPLACED);
        return true;
    }

    public boolean cancel(final long orderId) {
        if (!book.containsKey(orderId)) {
            return false;
        }
        book.get(orderId).cancel();
        return true;
    }

    public Order getOrder(final long orderId) {
        if (!book.containsKey(orderId)) {
            return null;
        }
        return book.get(orderId);
    }

    public String debug() {
        Optional<String> optional = book.entrySet().stream().map(entry -> entry.getValue().toString())
            .reduce((s1, s2) -> s1 + ", " + s2);
        return optional.isPresent() ? optional.get() : "";
    }

    public List<Order> trigger(Quote quote) {
        return Collections.emptyList();
    }

    public void clear() {
        this.book.clear();
    }

    public int size() {
        return this.book.size();
    }

    @Override
    public int snapshot(ExpandableDirectByteBuffer buffer, int offset) {
        int currentOffset = offset;
        MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
        OrdersRepoEncoder ordersRepoEncoder = new OrdersRepoEncoder();
        ordersRepoEncoder.wrapAndApplyHeader(buffer, currentOffset, headerEncoder);
        ordersRepoEncoder.size(book.size());
        currentOffset += headerEncoder.encodedLength() + ordersRepoEncoder.encodedLength();

        OrderEncoder orderEncoder = new OrderEncoder();
        for (Order order : book.values()) {
            orderEncoder.wrapAndApplyHeader(buffer, currentOffset, headerEncoder);
            order.extractTo(orderEncoder);

            currentOffset += headerEncoder.encodedLength() + orderEncoder.encodedLength();
        }
        return currentOffset;
    }

    @Override
    public void loadSnapshot(ExpandableDirectByteBuffer buffer, int offset, int length) {

        int currentOffset = offset;

        MessageHeaderDecoder headerDecoder = new MessageHeaderDecoder();
        headerDecoder.wrap(buffer, currentOffset);
        int headerLength = headerDecoder.encodedLength();
        int actingBlockLength = headerDecoder.blockLength();
        int actingVersion = headerDecoder.version();

        assert headerDecoder.templateId() == OrdersRepoDecoder.TEMPLATE_ID;

        OrdersRepoDecoder ordersRepoDecoder = new OrdersRepoDecoder();
        ordersRepoDecoder.wrap(buffer, currentOffset + headerLength, actingBlockLength, actingVersion);
        currentOffset += headerLength + ordersRepoDecoder.encodedLength();

        OrderDecoder orderDecoder = new OrderDecoder();
        long size = ordersRepoDecoder.size();
        for (int i = 0; i < size; i++) {
            headerDecoder.wrap(buffer, currentOffset);
            headerLength = headerDecoder.encodedLength();
            actingBlockLength = headerDecoder.blockLength();
            actingVersion = headerDecoder.version();

            assert headerDecoder.templateId() == OrderDecoder.TEMPLATE_ID;

            orderDecoder.wrap(buffer, currentOffset + headerLength, actingBlockLength, actingVersion);
            currentOffset += headerDecoder.encodedLength() + orderDecoder.encodedLength();

            Order order = new Order();
            order.extractFrom(orderDecoder);
            book.put(order.getOrderId(), order);
        }
    }
}
