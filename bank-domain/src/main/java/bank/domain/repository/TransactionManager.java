package bank.domain.repository;

import java.util.function.Supplier;

/**
 * @author ZhengHao Lou
 * Date    2021/10/26
 */
public interface TransactionManager<R> {
    <R> R execute(Supplier<R> supplier);
}
