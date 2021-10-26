package bank.persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
public class PersistenceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PersistenceApplication.class, args);
    }
}
