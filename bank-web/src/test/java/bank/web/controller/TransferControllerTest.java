package bank.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import bank.application.TransferService;
import bank.types.dto.TransferResultDto;
import bank.web.WebApplicationTest;
import java.math.BigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplicationTest.class)
public class TransferControllerTest {

    @Autowired
    private TransferController controller;

    @MockBean
    private TransferService transferService;

    @Test
    public void test1() {
        TransferResultDto resultDto = new TransferResultDto();
        resultDto.setResult(true);
        doReturn(resultDto).when(transferService).transfer(any());

        Long userId = 1L;
        String accountNumber = "002";
        BigDecimal targetAmount = BigDecimal.ONE;
        String targetCurrency = "CNY";
        controller.transfer(userId, accountNumber, targetAmount, targetCurrency);

    }

}
