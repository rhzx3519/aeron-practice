package bank.cluser.demuxer;

import bank.generated.order.MessageHeaderDecoder;
import bank.generated.order.MessageHeaderEncoder;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.aeron.cluster.service.ClientSession;
import io.aeron.cluster.service.Cluster;
import org.agrona.ExpandableDirectByteBuffer;

/**
 * @author ZhengHao Lou
 * Date    2022/01/14
 */
public abstract class BaseDemuxer {

    protected Cluster cluster;
    protected ClientSession session;
    protected long timestamp;
    protected Cluster.Role role;
    protected final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
    protected final MessageHeaderDecoder headerDecoder = new MessageHeaderDecoder();
    protected Config config = ConfigFactory.load();

    public void setSession(ClientSession session)
    {
        this.session = session;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public void setClusterTime(long timestamp)
    {
        this.timestamp = timestamp;
    }

    public void setRole(Cluster.Role newRole) {
        this.role = newRole;
    }

    public boolean isLeader() {
        return this.role == Cluster.Role.LEADER;
    }

    public abstract int takeSnapshot(ExpandableDirectByteBuffer buffer, int offset);

    public abstract void onTakeLeadership();

    public abstract void onLoseLeadership();

}
