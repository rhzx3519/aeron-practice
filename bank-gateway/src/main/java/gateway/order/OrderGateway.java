package gateway.order;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.aeron.Aeron;
import io.aeron.CommonContext;
import io.aeron.cluster.client.AeronCluster;
import io.aeron.driver.MediaDriver;
import io.aeron.driver.ThreadingMode;
import java.io.File;
import java.time.Duration;
import java.util.concurrent.locks.LockSupport;
import lombok.extern.slf4j.Slf4j;
import org.agrona.concurrent.AgentRunner;
import org.agrona.concurrent.IdleStrategy;
import org.agrona.concurrent.ShutdownSignalBarrier;
import org.agrona.concurrent.SleepingMillisIdleStrategy;

/**
 * @author ZhengHao Lou
 * Date    2022/01/14
 */
@Slf4j
public class OrderGateway {

    public static void main(final String[] args)
    {
        // load config
        final String configFilePath = System.getProperty("config.file");
        Config config = ConfigFactory.parseFile(new File(configFilePath));
        final String subChannel = config.getString("gateway.subscription.channel");
        final int subStreamId = config.getInt("gateway.subscription.streamId");
        final String clusterIngressChannel = config.getString("order.cluster.ingress.channel");
        final String clusterIngressEndpoints = config.getString("order.cluster.ingress.endpoints");
        final int clusterIngressStreamId = config.getInt("order.cluster.ingress.streamId");

        final String clusterEgressChannel = config.getString("order.cluster.egress.channel");


        // init
        initSub(subChannel, subStreamId);

        OrderClusterEgressListener orderClusterListener = new OrderClusterEgressListener();
        final ShutdownSignalBarrier barrier = new ShutdownSignalBarrier();

        try (
            MediaDriver mediaDriver = MediaDriver.launchEmbedded(new MediaDriver.Context()
                .threadingMode(ThreadingMode.SHARED)
                .dirDeleteOnStart(true)
                .dirDeleteOnShutdown(true));
            AeronCluster aeronCluster = AeronCluster.connect(
                new AeronCluster.Context()
                    .egressListener(orderClusterListener)
                    .egressChannel(clusterEgressChannel)
                    .aeronDirectoryName(mediaDriver.aeronDirectoryName())
                    .ingressChannel(clusterIngressChannel)
                    .ingressEndpoints(clusterIngressEndpoints)
                    .ingressStreamId(clusterIngressStreamId)
                    .messageTimeoutNs(Duration.ofSeconds(5).toNanos()))
        ) {
            log.info("{}, {}", aeronCluster.egressSubscription().channel(), aeronCluster.egressSubscription().streamId());
            log.info("{}, {}, {}", aeronCluster.ingressPublication().channel(), aeronCluster.ingressPublication().streamId(),
                aeronCluster.ingressPublication().channelStatus());
            aeronCluster.onNewLeader(aeronCluster.clusterSessionId(), aeronCluster.leadershipTermId(),
                aeronCluster.leaderMemberId(), clusterIngressEndpoints);
            orderClusterListener.setAeronCluster(aeronCluster);
            orderClusterListener.start();

            barrier.await();
        }
    }

    private static void initSub(String channel, int streamId) {
        //construct Media Driver, cleaning up media driver folder on start/stop
        final MediaDriver.Context mediaDriverCtx = new MediaDriver.Context()
            .aeronDirectoryName(CommonContext.getAeronDirectoryName() + "-client")
            .dirDeleteOnStart(true)
            .dirDeleteOnShutdown(true)
            .threadingMode(ThreadingMode.SHARED)
            .mtuLength(1024)
            ;
        final MediaDriver mediaDriver = MediaDriver.launchEmbedded(mediaDriverCtx);

        //construct Aeron, pointing at the media driver's folder
        final Aeron.Context aeronCtx = new Aeron.Context()
            .aeronDirectoryName(mediaDriver.aeronDirectoryName());
        final Aeron aeron = Aeron.connect(aeronCtx);

        final IdleStrategy idleStrategyClient = new SleepingMillisIdleStrategy();
        //Construct the client agent
        ClientAgent clientAgent = new ClientAgent(aeron, channel, streamId);
        AgentRunner clientAgentRunner = new AgentRunner(idleStrategyClient, Throwable::printStackTrace,
            null, clientAgent);
        AgentRunner.startOnThread(clientAgentRunner);
    }

}
