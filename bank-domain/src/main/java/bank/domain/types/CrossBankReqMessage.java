package bank.domain.types;

import bank.types.AccountNumber;
import bank.types.Money;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ZhengHao Lou
 * Date    2021/10/26
 *
 * 跨行转账的
 */
@Data
@AllArgsConstructor
public class CrossBankReqMessage {
    private Long transactionId;
    private String targetBank;
    private AccountNumber source;
    private AccountNumber target;
    private Money money;
    private Date date;

    public String serialize() {
        return transactionId + "," + targetBank + "," + source + "," + target + "," + money + "," + date;
    }
}
