package bank.types.trigger;

/**
 * @author ZhengHao Lou
 * Date    2021/10/26
 */
public enum CommandStatus {
    PENDING,
    CANCELED,
    EXECUTED;

    public boolean closed() {
        return this.ordinal() >= 1;
    }
}
