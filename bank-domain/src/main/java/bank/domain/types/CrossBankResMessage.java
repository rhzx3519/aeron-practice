package bank.domain.types;

import bank.types.AccountNumber;
import bank.types.Money;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ZhengHao Lou
 * Date    2021/10/26
 */
@Data
@AllArgsConstructor
public class CrossBankResMessage {
    private Long transactionId;
    private AccountNumber source;
    private Money money;
    private Boolean result;
}
