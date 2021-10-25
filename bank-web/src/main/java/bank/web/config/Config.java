package bank.web.config;

import bank.application.TransferService;
import bank.application.impl.TransferServiceImpl;
import bank.external.impl.ExchangeRateServiceImpl;
import bank.messaging.impl.AuditMessageProducerImpl;
import bank.repository.iml.AccountRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
@Configuration
public class Config {

    @Autowired
    private AccountRepositoryImpl accountRepository;

    @Autowired
    private ExchangeRateServiceImpl exchangeRateService;

    @Autowired
    private AuditMessageProducerImpl auditMessageProducer;

    @Bean
    public TransferService transferService() {
        TransferServiceImpl transferService = new TransferServiceImpl();
        transferService.setAccountRepository(accountRepository);
        return transferService;
    }
}
