package bank.cluser.demuxer;

import io.aeron.logbuffer.FragmentHandler;
import io.aeron.logbuffer.Header;
import org.agrona.DirectBuffer;
import org.agrona.ExpandableDirectByteBuffer;

/**
 * @author ZhengHao Lou
 * Date    2022/01/14
 */
public class QuoteDemuxer extends BaseDemuxer implements FragmentHandler {

    @Override
    public void onFragment(DirectBuffer buffer, int offset, int length, Header header) {

    }

    @Override
    public int takeSnapshot(ExpandableDirectByteBuffer buffer, int offset) {
        return offset;
    }

    @Override
    public void onTakeLeadership() {

    }

    @Override
    public void onLoseLeadership() {

    }
}
