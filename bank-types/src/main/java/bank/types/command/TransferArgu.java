package bank.types.command;

import bank.types.AccountNumber;
import bank.types.Money;
import bank.types.UserId;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ZhengHao Lou
 * Date    2021/10/25
 */
@Data
@AllArgsConstructor
public class TransferArgu implements Argument{
    private UserId sourceUserId;
    private AccountNumber targetAccountNumber;
    private BigDecimal targetAmount;
    private String targetCurrency;
}
