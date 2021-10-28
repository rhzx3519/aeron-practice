package bank.web.gateway.innotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ZhengHao Lou
 * Date    2021/10/28
 *
 * 用于{@link bank.web.gateway.impl.TransferGateway} 处理 controller 参数、异常和返回值
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TransferHandler {

}
