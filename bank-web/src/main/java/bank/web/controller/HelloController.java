package bank.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhengHao Lou
 * Date    2021/10/28
 */
@RestController
@RequestMapping("/bank")
public class HelloController {

    @GetMapping(value = "/hello")
    public String hello() {

        return "hello";
    }
}
