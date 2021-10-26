package bank.domain.messaging;

import bank.domain.types.CrossBankReqMessage;
import bank.domain.types.SendResult;

/**
 * @author ZhengHao Lou
 * Date    2021/10/26
 */
public interface CrossBankTransferMessageProducer {

    SendResult<Boolean> send(CrossBankReqMessage message);

}
