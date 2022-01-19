package gateway.order;

import gateway.order.config.Constants;
import io.aeron.Aeron;
import io.aeron.FragmentAssembler;
import io.aeron.Subscription;
import org.agrona.concurrent.Agent;

/**
 * @author ZhengHao Lou
 * Date    2022/01/16
 */
public class ClientAgent implements Agent {

    private Subscription subscription;
    private final Aeron aeron;
    private final OrderDemuxer orderDemuxer;
    private final String channel;
    private final int streamId;

    public ClientAgent(Aeron aeron, String channel, int streamId) {
        this.aeron = aeron;
        this.orderDemuxer = new OrderDemuxer();
        this.channel = channel;
        this.streamId = streamId;
    }

    @Override
    public void onStart() {
        this.subscription = aeron.addSubscription(channel, streamId);
    }

    @Override
    public int doWork() throws Exception {
        return this.subscription.poll(new FragmentAssembler(orderDemuxer), 1<<4);
    }

    @Override
    public void onClose() {
        this.orderDemuxer.onClose();
    }

    @Override
    public String roleName() {
        return "Order gateway client";
    }
}
