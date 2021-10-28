package bank.types.command;

import bank.types.AccountNumber;
import bank.types.UserId;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * @author ZhengHao Lou
 * Date    2021/10/27
 *
 * 转账请求入参
 */
@Data
@Builder
public class TransferCommand {
    @NonNull
    private UserId sourceUserId;
    @NonNull
    private AccountNumber targetAccountNumber;
    @NonNull
    private BigDecimal targetAmount;
    @NonNull
    private String targetCurrency;
}
