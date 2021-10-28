package bank.types.trigger;

/**
 * @author ZhengHao Lou
 * Date    2021/10/26
 */
public enum EventStatus {
    PENDING,
    CANCELED,
    TRIGGERED;

    public boolean closed() {
        return this.ordinal() >= 1;
    }
}
