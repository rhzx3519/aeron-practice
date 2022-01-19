package bank.cluser;

import bank.cluser.services.OrderClusterService;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.aeron.ChannelUriStringBuilder;
import io.aeron.CommonContext;
import io.aeron.archive.Archive;
import io.aeron.archive.ArchiveThreadingMode;
import io.aeron.archive.client.AeronArchive;
import io.aeron.cluster.ClusteredMediaDriver;
import io.aeron.cluster.ConsensusModule;
import io.aeron.cluster.service.ClusteredServiceContainer;
import io.aeron.driver.MediaDriver;
import io.aeron.driver.MinMulticastFlowControlSupplier;
import io.aeron.driver.ThreadingMode;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.agrona.ErrorHandler;
import org.agrona.concurrent.NoOpLock;
import org.agrona.concurrent.ShutdownSignalBarrier;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author ZhengHao Lou
 * Date    2022/01/14
 */
@Slf4j
public class OrderCluster {

    private static final String CONF_HOSTNAMES_KEY = "aeron.cluster.hostnames";

    private static final int INGRESS_PORT = 9001;
    private static final int CONSENSUS_PORT = 9002;
    private static final int LOG_PORT = 9003;
    private static final int CATCHUP_PORT = 9004;
    private static final int ARCHIVE_PORT = 9005;
    private static final int ARCHIVE_CONTROL_PORT = 9006;
    public static final String ARCHIVE_SUB_DIR = "archive";
    public static final String CLUSTER_SUB_DIR = "cluster";

    public static void main(final String[] args)
    {
        final String configFilePath = System.getProperty("config.file");
        Config config = ConfigFactory.parseFile(new File(configFilePath));
        final int nodeId = Integer.parseInt(System.getProperty("nodeId"));
        final String hostnamesStr = config.getString(CONF_HOSTNAMES_KEY);

        log.info("nodeId: {}, hostnamesStr: {}", nodeId, hostnamesStr);

        final ShutdownSignalBarrier barrier = new ShutdownSignalBarrier();

        String[] hostnames = hostnamesStr.split(",");

        final String clusterMembers = clusterMembers(hostnames);

        final String aeronDirName = CommonContext.getAeronDirectoryName() + "-" + nodeId + "-driver";
        final File baseDir = new File(System.getProperty("user.dir"), "aeron-cluster-" + nodeId);

        final String hostname = hostnames[nodeId];

        // config
        final MediaDriver.Context mediaDriverContext = new MediaDriver.Context()
            .dirDeleteOnStart(true)
            .dirDeleteOnShutdown(true)
            .aeronDirectoryName(aeronDirName)
            .threadingMode(ThreadingMode.SHARED)
            .termBufferSparseFile(true)
            .multicastFlowControlSupplier(new MinMulticastFlowControlSupplier())
            .terminationHook(barrier::signal);

        final AeronArchive.Context replicationArchiveContext = new AeronArchive.Context()
            .controlResponseChannel("aeron:udp?endpoint=localhost:9021");

        final Archive.Context archiveContext = new Archive.Context()
            .aeronDirectoryName(aeronDirName)
            .archiveDir(new File(baseDir, ARCHIVE_SUB_DIR))
            .controlChannel(udpChannel(nodeId, "localhost", ARCHIVE_CONTROL_PORT))
            .archiveClientContext(replicationArchiveContext)
            .localControlChannel("aeron:ipc?term-length=64k")
            .recordingEventsEnabled(false)
            .threadingMode(ArchiveThreadingMode.SHARED);

        final AeronArchive.Context aeronArchiveContext = new AeronArchive.Context()
            .lock(NoOpLock.INSTANCE)
            .controlRequestChannel(archiveContext.localControlChannel())
            .controlRequestStreamId(archiveContext.localControlStreamId())
            .controlResponseChannel(archiveContext.localControlChannel())
            .aeronDirectoryName(aeronDirName);


        final ConsensusModule.Context consensusModuleContext = new ConsensusModule.Context()
            .clusterMemberId(nodeId)
            .clusterMembers(clusterMembers)
            .clusterDir(new File(baseDir, CLUSTER_SUB_DIR))
            .archiveContext(aeronArchiveContext.clone())
            .serviceCount(1)
            .appointedLeaderId(-1)
            .replicationChannel("aeron:udp?endpoint=localhost:9005");


        final String ingressChannel = config.getString("aeron.cluster.ingressChannel");
        final int ingressStreamId = config.getInt("aeron.cluster.ingressStreamId");
        consensusModuleContext.ingressChannel(ingressChannel);
        consensusModuleContext.ingressStreamId(ingressStreamId);


        final List<ClusteredServiceContainer.Context> serviceContexts = new ArrayList<>();

        final ClusteredServiceContainer.Context clusteredServiceContext = new ClusteredServiceContainer.Context()
            .aeronDirectoryName(aeronDirName)
            .archiveContext(aeronArchiveContext.clone())
            .clusterDir(new File(baseDir, CLUSTER_SUB_DIR))
            .clusteredService(new OrderClusterService())
            .serviceId(0);
        serviceContexts.add(clusteredServiceContext);

        mediaDriverContext.errorHandler(errorHandler("Media Driver"));
        archiveContext.errorHandler(errorHandler("Archive"));
        aeronArchiveContext.errorHandler(errorHandler("Aeron Archive"));
        consensusModuleContext.errorHandler(errorHandler("Consensus Module"));
        clusteredServiceContext.errorHandler(errorHandler("Clustered Service"));

        System.out.println(consensusModuleContext.consensusChannel());
//        System.out.println(consensusModuleContext.consensusModuleStreamId());
        System.out.println(consensusModuleContext.clusterConsensusEndpoints());
        System.out.println(consensusModuleContext.logChannel());
//        System.out.println(consensusModuleContext.ingressChannel());
//        System.out.println(consensusModuleContext.ingressStreamId());

        try (
            ClusteredMediaDriver clusteredMediaDriver = ClusteredMediaDriver.launch(
                mediaDriverContext,
                archiveContext,
                consensusModuleContext);
            ClusteredServiceContainer container = ClusteredServiceContainer.launch(
                clusteredServiceContext))
        {
            log.info("[{}] Started Cluster Node...", nodeId);
            barrier.await();
            log.info("[{}] Exiting...", nodeId);
        }

    }

    private static ErrorHandler errorHandler(final String context)
    {
        return
            (Throwable throwable) ->
            {
                System.err.println(context);
                throwable.printStackTrace(System.err);
            };
    }

    /**
     * String representing the cluster members.
     * <p>
     * <code>
     * 0,ingress:port,consensus:port,log:port,catchup:port,archive:port| \
     * 1,ingress:port,consensus:port,log:port,catchup:port,archive:port| ...
     * </code>
     * <p>
     * @param hostnames
     * @return
     */
    private static String clusterMembers(String[] hostnames)
    {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hostnames.length; i++)
        {
            sb.append(i);
            sb.append(',').append(endpoint(hostnames[i], INGRESS_PORT));
            sb.append(',').append(endpoint(hostnames[i], CONSENSUS_PORT));
            sb.append(',').append(endpoint(hostnames[i], LOG_PORT));
            sb.append(',').append(endpoint(hostnames[i], CATCHUP_PORT));
            sb.append(',').append(endpoint(hostnames[i], ARCHIVE_PORT));
            sb.append('|');
        }

        return sb.toString();
    }

    private static String endpoint(String hostname, int port) {
        return hostname + ":" + port;
    }

    private static String udpChannel(final int nodeId, final String hostname, final int port)
    {
        return "aeron:udp?endpoint=" + hostname + ":" + port;
    }

    private static String logControlChannel(final String hostname, final int port) {
        return new ChannelUriStringBuilder()
            .media(CommonContext.UDP_MEDIA)
            .controlMode(CommonContext.MDC_CONTROL_MODE_MANUAL)
            .controlEndpoint(hostname + ":" + port)
            .termLength(512 * 1024 * 1024)
            .build();
    }
}
