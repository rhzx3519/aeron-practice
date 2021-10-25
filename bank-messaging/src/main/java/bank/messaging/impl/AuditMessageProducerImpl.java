package bank.messaging.impl;

import bank.domain.messaging.AuditMessageProducer;
import bank.domain.types.AuditMessage;
import bank.domain.types.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
@Service
public class AuditMessageProducerImpl implements AuditMessageProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private static final String TOPIC_AUDIT_LOG = "TOPIC_AUDIT_LOG";

    @Override
    public SendResult<Boolean> send(AuditMessage message) {
        String messageBody = message.serialize();
        kafkaTemplate.send(TOPIC_AUDIT_LOG, messageBody);
        return SendResult.ok(true);
    }

}
