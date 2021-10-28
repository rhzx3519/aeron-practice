package bank.web.controller;

import bank.application.types.Result;
import bank.web.gateway.innotations.TransferHandler;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
@RestController
@RequestMapping("/bank")
public class TransferController {

//    @Autowired
//    private TransferGateway transferGateway;
//
//    @Autowired
//    private TransferService transferService;

    @TransferHandler
    @PostMapping(value = "/transfer")
    public <T> Result<T> transfer(@RequestParam("userId") Long userId, @RequestParam("accountNumber") String accountNumber,
                           @RequestParam("targetAmount") BigDecimal targetAmount,
                           @RequestParam("targetCurrency") String targetCurrency) {

        return Result.ok();
    }
}
