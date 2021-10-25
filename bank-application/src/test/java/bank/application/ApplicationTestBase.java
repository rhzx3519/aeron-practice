package bank.application;

import bank.domain.external.ExchangeRateService;
import bank.domain.messaging.AuditMessageProducer;
import bank.domain.repository.AccountRepository;
import bank.domain.service.AccountTransferService;
import bank.domain.service.TimerService;
import bank.domain.service.impl.AccountTransferServiceImpl;
import bank.domain.service.impl.TimerServiceImpl;
import org.mockito.Mockito;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
public class ApplicationTestBase {

    protected AccountTransferService accountTransferService = new AccountTransferServiceImpl();

    protected TimerService timerService = new TimerServiceImpl();

    protected ExchangeRateService exchangeRateService = Mockito.mock(ExchangeRateService.class);

    protected AccountRepository accountRepository = Mockito.mock(AccountRepository.class);

    protected AuditMessageProducer auditMessageProducer = Mockito.mock(AuditMessageProducer.class);

}
