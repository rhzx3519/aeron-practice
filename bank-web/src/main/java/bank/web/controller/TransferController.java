package bank.web.controller;

import bank.application.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
@Controller
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping(value = "/transfer")
    public void transfer() {

    }
}
