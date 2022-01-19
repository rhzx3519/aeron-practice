package gateway.order;

import bank.generated.order.EventTypeEnum;
import bank.generated.order.MessageHeaderEncoder;
import bank.generated.order.OrderActionEnum;
import bank.generated.order.OrderRequestMessageEncoder;
import bank.generated.order.OrderTypeEnum;
import io.aeron.cluster.client.AeronCluster;
import io.aeron.cluster.client.EgressListener;
import io.aeron.cluster.codecs.EventCode;
import io.aeron.logbuffer.Header;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.agrona.DirectBuffer;
import org.agrona.ExpandableDirectByteBuffer;
import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.IdleStrategy;
import org.agrona.concurrent.SleepingMillisIdleStrategy;

/**
 * @author ZhengHao Lou
 * Date    2022/01/14
 */
@Slf4j
public class OrderClusterEgressListener implements EgressListener {

    private final ExpandableDirectByteBuffer buffer = new ExpandableDirectByteBuffer(1 << 10);
    private final IdleStrategy idleStrategy = new SleepingMillisIdleStrategy();
    private final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
    private final OrderRequestMessageEncoder orderRequestEncoder = new OrderRequestMessageEncoder();
    private AeronCluster aeronCluster;

    public void start() {
        orderRequestEncoder.wrapAndApplyHeader(buffer, 0, headerEncoder);
        orderRequestEncoder.correlationId(ThreadLocalRandom.current().nextLong());
        orderRequestEncoder.orderId(ThreadLocalRandom.current().nextLong());
        orderRequestEncoder.userId(ThreadLocalRandom.current().nextLong());
        orderRequestEncoder.accountId(ThreadLocalRandom.current().nextLong());
        orderRequestEncoder.orderType(OrderTypeEnum.LIMIT);
        orderRequestEncoder.eventType(EventTypeEnum.PLACE);
        orderRequestEncoder.limitPrice(5.9);
        orderRequestEncoder.symbol("AAPL");
        orderRequestEncoder.totalQuantity(100);
        orderRequestEncoder.orderAction(OrderActionEnum.BUY);
        offer(buffer, 0, headerEncoder.encodedLength() + orderRequestEncoder.encodedLength());
        log.info("Offered an order to cluster...");

        while (!Thread.currentThread().isInterrupted())
        {
            aeronCluster.sendKeepAlive();
            idleStrategy.idle(aeronCluster.pollEgress());
        }
    }

    @Override
    public void onSessionEvent(long correlationId, long clusterSessionId, long leadershipTermId, int leaderMemberId,
                               EventCode code, String detail) {
        log.info("onSessionEvent, correlationId: {}, clusterSessionId: {}, leadershipTermId: {}, leaderMemberId: {}, EventCode: {}, detail: {}",
            correlationId, clusterSessionId, leadershipTermId, leaderMemberId, code, detail);
    }

    @Override
    public void onNewLeader(long clusterSessionId, long leadershipTermId, int leaderMemberId, String ingressEndpoints) {
        log.info("onNewLeader, clusterSessionId: {}, leadershipTermId: {}, leaderMemberId: {}, ingressEndpoints: {}",
            clusterSessionId, leadershipTermId, leaderMemberId, ingressEndpoints);
    }

    @Override
    public void onMessage(long clusterSessionId, long timestamp, DirectBuffer buffer, int offset, int length,
                          Header header) {
        log.info("Receive cluster message, clusterSessionId: {}, timestamp: {}", clusterSessionId, timestamp);

    }

    public void setAeronCluster(AeronCluster clusterClient)
    {
        this.aeronCluster = clusterClient;
    }

    private void offer(MutableDirectBuffer buffer, int offset, int length)
    {
        while (aeronCluster.offer(buffer, offset, length) < 0)
        {
            idleStrategy.idle(aeronCluster.pollEgress());
        }
    }
}
