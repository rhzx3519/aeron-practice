package bank.cluser.services;

import bank.cluser.demuxer.MasterDemuxer;
import io.aeron.ExclusivePublication;
import io.aeron.FragmentAssembler;
import io.aeron.Image;
import io.aeron.cluster.codecs.CloseReason;
import io.aeron.cluster.service.ClientSession;
import io.aeron.cluster.service.Cluster;
import io.aeron.cluster.service.ClusteredService;
import io.aeron.logbuffer.Header;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.agrona.DirectBuffer;
import org.agrona.ExpandableDirectByteBuffer;

/**
 * @author ZhengHao Lou
 * Date    2022/01/13
 */
@Slf4j
public class OrderClusterService implements ClusteredService {

    private Cluster cluster;
    private ClientSession currentSession;
    private MasterDemuxer demuxer;

    public OrderClusterService() {
        this.demuxer = new MasterDemuxer();
    }


    @Override
    public void onStart(Cluster cluster, Image snapshotImage) {
        this.cluster = cluster;
        this.demuxer.setCluster(cluster);
        if (snapshotImage != null)
        {
            log.info("loading snapshot...");
            do
            {
                int polledItemCount = snapshotImage.poll(new FragmentAssembler(demuxer), 64);
                if (polledItemCount == 0)
                {
                    log.info("No more items in snapshot...");
                    break;
                }
            }
            while (true);
        }
    }

    @Override
    public void onSessionOpen(ClientSession session, long timestamp) {
        log.info("Cluster Client Session opened, responseChannel: {}, responseStreamId: {}, timestamp: {}",
            session.responseChannel(), session.responseStreamId(), timestamp);
    }

    @Override
    public void onSessionClose(ClientSession session, long timestamp, CloseReason closeReason) {
        log.info("Cluster Client Session closed, responseChannel: {}, responseStreamId: {}, timestamp: {}, closeReason: {}",
            session.responseChannel(), session.responseStreamId(), timestamp, closeReason);

    }

    @Override
    public void onSessionMessage(ClientSession session, long timestamp, DirectBuffer buffer, int offset, int length,
                                 Header header) {
        demuxer.setSession(session);
        demuxer.setClusterTime(timestamp);
        demuxer.onFragment(buffer, offset, length, header);
    }

    @Override
    public void onTimerEvent(long correlationId, long timestamp) {

    }

    @Override
    public void onTakeSnapshot(ExclusivePublication snapshotPublication) {
        log.info("taking snapshot");
        final ExpandableDirectByteBuffer buffer = new ExpandableDirectByteBuffer(1<<10);

        //get the current value from the state machine
        int length = this.demuxer.takeSnapshot(buffer, 0);

        //offer the snapshot to the publication
        snapshotPublication.offer(buffer, 0, length);
    }

    @Override
    public void onRoleChange(Cluster.Role newRole) {
        log.info("onRoleChange, memberId: {}, role: {}", this.cluster.memberId(), newRole);
        this.demuxer.setRole(newRole);
    }

    @Override
    public void onTerminate(Cluster cluster) {
        log.info("Cluster is terminated...");
    }

    @Override
    public void onNewLeadershipTermEvent(long leadershipTermId, long logPosition, long timestamp,
                                         long termBaseLogPosition, int leaderMemberId, int logSessionId,
                                         TimeUnit timeUnit, int appVersion) {
        ClusteredService.super.onNewLeadershipTermEvent(leadershipTermId, logPosition, timestamp, termBaseLogPosition,
            leaderMemberId, logSessionId, timeUnit, appVersion);

    }
}
