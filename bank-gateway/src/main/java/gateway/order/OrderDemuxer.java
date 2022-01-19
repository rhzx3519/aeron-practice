package gateway.order;

import io.aeron.logbuffer.FragmentHandler;
import io.aeron.logbuffer.Header;
import lombok.extern.slf4j.Slf4j;
import org.agrona.DirectBuffer;

/**
 * @author ZhengHao Lou
 * Date    2022/01/16
 */
@Slf4j
public class OrderDemuxer implements FragmentHandler {
    @Override
    public void onFragment(DirectBuffer buffer, int offset, int length, Header header) {
        log.info("Receive cluster message");
    }

    public void onClose() {}
}
