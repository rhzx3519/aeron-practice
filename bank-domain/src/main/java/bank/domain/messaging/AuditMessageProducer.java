package bank.domain.messaging;

import bank.domain.types.AuditMessage;
import bank.domain.types.SendResult;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
public interface AuditMessageProducer {

    SendResult<Boolean> send(AuditMessage message);

}
