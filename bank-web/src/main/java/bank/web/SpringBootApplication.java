package bank.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
@ComponentScan(basePackages = "bank")
@EnableAsync
@EnableScheduling
public class SpringBootApplication {

}
