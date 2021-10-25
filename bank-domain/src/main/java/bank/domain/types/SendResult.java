package bank.domain.types;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
public class SendResult<T> {

    public static <T> SendResult<T> ok(T data) {
        SendResult<T> result = new SendResult<>();
        return result;
    }

}
