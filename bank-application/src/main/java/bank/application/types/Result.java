package bank.application.types;

import lombok.AllArgsConstructor;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
@AllArgsConstructor
public class Result<T> {

    private boolean result;
    private T data;

    private static final Result<String> OK = new Result(true, "");

    public static Result ok() {
        return OK;
    }

    public static <T> Result<T> failed(T data) {
        Result<T> result = new Result<>(false, data);
        return result;
    }

    public boolean isOk() {
        return this == OK;
    }
}
