package bank.application.types;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
public class Result<T> {

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        return result;
    }
}
