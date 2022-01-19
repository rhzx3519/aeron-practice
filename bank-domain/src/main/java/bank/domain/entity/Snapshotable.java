package bank.domain.entity;

import org.agrona.ExpandableDirectByteBuffer;

/**
 * @author ZhengHao Lou
 * Date    2022/01/13
 */
public interface Snapshotable {

    int snapshot(ExpandableDirectByteBuffer buffer, int offset);

    void loadSnapshot(ExpandableDirectByteBuffer buffer, int offset, int length);
}
