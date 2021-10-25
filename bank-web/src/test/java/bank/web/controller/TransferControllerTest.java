package bank.web.controller;

import bank.web.SpringBootApplicationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApplicationTest.class)
public class TransferControllerTest {

    @Autowired
    private TransferController controller;

    @Test
    public void test1() {

    }

}
