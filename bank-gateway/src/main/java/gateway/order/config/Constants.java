package gateway.order.config;

/**
 * @author ZhengHao Lou
 * Date    2022/01/16
 */
public interface Constants {

    String CLUSTER_INGRESS_CHANNEL = "aeron:udp?term-length=64k";
    String CLUSTER_INGRESS_ENDPOINTS = "0=order-cluster0:9010,1=order-cluster1:9010,2=order-cluster2:9010";
//    String CLUSTER_INGRESS_ENDPOINTS = "0=localhost:9010";
    int CLUSTER_INGRESS_STREAM_ID = 10;
    String CLIENT_SUBSCRIPTION_CHANNEL = "aeron:udp?endpoint=localhost:8000|term-length=64k";
    int CLIENT_SUBSCRIPTION_STREAM_ID = 80;
}
