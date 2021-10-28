package bank.web.gateway.impl;

import bank.application.TransferService;
import bank.application.types.Result;
import bank.types.AccountNumber;
import bank.types.UserId;
import bank.types.command.TransferCommand;
import bank.types.dto.TransferResultDto;
import bank.web.gateway.BankGateway;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ZhengHao Lou
 * Date    2021/10/28
 */
@Slf4j
@Aspect
@Component
public class TransferGateway implements BankGateway {

    @Autowired
    private TransferService transferService;

    @Around("@annotation(bank.web.gateway.innotations.TransferHandler)")
    public Object route(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = null;
        try {
            log.info("TransferGateway process...");
            Object[] args = joinPoint.getArgs();
            proceed = process(args);
//            proceed = joinPoint.proceed();
        } catch (IllegalArgumentException e) {
            log.error("", e);
            return Result.failed(e.getMessage());
        } catch (Exception e) {
            log.error("", e);
            return Result.failed(e.getMessage());
        }
        return proceed;
    }

    private Result<?> process(Object[] args) {
        // 1) 参数校验
        Long userId = (Long) args[0];
        String accountNumber = (String) args[1];
        BigDecimal targetAmount = (BigDecimal) args[2];
        String targetCurrency = (String) args[3];
        // 2) Application service 入参封装
        TransferCommand transferCommand = TransferCommand.builder().sourceUserId(new UserId(userId))
            .targetAccountNumber(new AccountNumber(accountNumber))
            .targetAmount(targetAmount).targetCurrency(targetCurrency).build();

        // 3) 调用Application service
        TransferResultDto resultDto = transferService.transfer(transferCommand);

        // 4) 封装返回给controller的结果
        if (!resultDto.getResult()) {
            return Result.failed("Transfer failed.");
        }
        return Result.ok();
    }
}
