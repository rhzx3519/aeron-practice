package bank.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
@ComponentScan(basePackages = {"bank"})
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableAsync
@EnableScheduling
public class WebApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(WebApplicationTest.class, args);
    }
}
