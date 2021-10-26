package bank.types.command;

/**
 * @author ZhengHao Lou
 * Date    2021/10/26
 */
public enum CommandStatus {
    PENDING,
    CANCELED,
    FAILED,
    SUCCESS;

    public boolean closed() {
        return this.ordinal() >= 1;
    }
}
