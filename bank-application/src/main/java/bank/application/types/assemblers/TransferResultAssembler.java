package bank.application.types.assemblers;

import bank.domain.entity.Account;
import bank.types.Money;
import bank.types.dto.TransferResultDto;

/**
 * @author ZhengHao Lou
 * Date    2021/10/27
 */
public class TransferResultAssembler {
    private TransferResultAssembler() {}
    public static final TransferResultAssembler INSTANCE = new TransferResultAssembler();

    public TransferResultDto assemble(Account account, Money money, Boolean result) {
        TransferResultDto resultDto = new TransferResultDto();
        resultDto.setAccountNumber(account.getAccountNumber().getValue());
        resultDto.setAmount(money.getAmount());
        resultDto.setCurrency(money.getCurrency().name());
        resultDto.setResult(result);
        return resultDto;
    }
}
