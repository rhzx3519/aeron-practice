package bank.cluser.demuxer;

import bank.generated.order.OrderRequestMessageDecoder;
import io.aeron.cluster.service.ClientSession;
import io.aeron.cluster.service.Cluster;
import io.aeron.logbuffer.FragmentHandler;
import io.aeron.logbuffer.Header;
import lombok.extern.slf4j.Slf4j;
import org.agrona.DirectBuffer;
import org.agrona.ExpandableDirectByteBuffer;

/**
 * @author ZhengHao Lou
 * Date    2022/01/13
 */
@Slf4j
public class MasterDemuxer extends BaseDemuxer implements FragmentHandler {

//    private final Aeron aeron;
//    private final ShutdownSignalBarrier barrier;

    private final OrderDemuxer orderDemuxer;
    private final QuoteDemuxer quoteDemuxer;

    public MasterDemuxer() {
        this.orderDemuxer = new OrderDemuxer();
        this.quoteDemuxer = new QuoteDemuxer();
    }

    @Override
    public void onFragment(DirectBuffer buffer, int offset, int length, Header header) {
        headerDecoder.wrap(buffer, offset);
        switch (headerDecoder.templateId()) {
            case OrderRequestMessageDecoder.TEMPLATE_ID:
                orderDemuxer.onFragment(buffer, offset, length, header);
                break;
            default:
                log.error("Unknow message, {}", headerDecoder.templateId());
                break;
        }
    }

    @Override
    public void setSession(ClientSession session)
    {
        this.orderDemuxer.setSession(session);
        this.quoteDemuxer.setSession(session);
    }

    @Override
    public void setClusterTime(long timestamp)
    {
        this.orderDemuxer.setClusterTime(timestamp);
        this.quoteDemuxer.setClusterTime(timestamp);
    }

    @Override
    public void setCluster(Cluster cluster) {
        this.orderDemuxer.setCluster(cluster);
        this.quoteDemuxer.setCluster(cluster);
    }

    @Override
    public int takeSnapshot(ExpandableDirectByteBuffer buffer, int offset) {
        int currentOffset = offset;
        currentOffset = this.orderDemuxer.takeSnapshot(buffer, currentOffset);
        currentOffset = this.quoteDemuxer.takeSnapshot(buffer, currentOffset);
        return currentOffset;
    }

    @Override
    public void onTakeLeadership() {
        this.orderDemuxer.onTakeLeadership();
        this.quoteDemuxer.onTakeLeadership();
    }

    @Override
    public void onLoseLeadership() {
        this.orderDemuxer.onLoseLeadership();
        this.quoteDemuxer.onLoseLeadership();
    }

    @Override
    public void setRole(Cluster.Role newRole) {
        super.setRole(newRole);
        this.orderDemuxer.setRole(newRole);
        this.quoteDemuxer.setRole(newRole);

        if (isLeader()) {
            this.onTakeLeadership();
        } else {
            this.onLoseLeadership();
        }
    }
}
