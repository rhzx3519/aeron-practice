package bank.web.controller;

import bank.application.TransferService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
@RestController
@RequestMapping("/quote")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping(value = "/transfer")
    public void transfer(@RequestParam("userId") Long userId, @RequestParam("accountNumber") String accountNumber,
                         @RequestParam("targetAmount") BigDecimal targetAmount,
                         @RequestParam("targetCurrency") String targetCurrency) {


    }
}
