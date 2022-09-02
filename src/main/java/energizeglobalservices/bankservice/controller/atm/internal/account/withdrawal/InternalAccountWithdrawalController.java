package energizeglobalservices.bankservice.controller.atm.internal.account.withdrawal;

import energizeglobalservices.bankservice.domain.Transaction;
import energizeglobalservices.bankservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class InternalAccountWithdrawalController {

    private final AccountService accountService;

    @PostMapping(path = "${apis.internal}/bank-accounts/{bankAccountId}/withdrawal")
    public InternalAccountWithdrawalResponse handle(@PathVariable("bankAccountId") String bankAccountId,
                                                    @RequestBody @Valid InternalAccountWithdrawRequest request){

        Transaction transaction = accountService.depositOrWithdrawalAmount(
                Long.valueOf(bankAccountId), request.getFingerPrint(),
                request.getAmount(), Transaction.TransactionType.CASH_WITHDRAW);

        return InternalAccountWithdrawalResponse.builder()
                .balance(transaction.getCard().getAccount().getBalance())
                .amount(transaction.getAmount())
                .accountId(transaction.getCard().getAccount().getId())
                .cardId(transaction.getCard() == null ? transaction.getCard().getId() : null)
                .cardNumber(transaction.getCard() == null ? transaction.getCard().getCardNumber() : StringUtils.EMPTY)
                .transactionKey(transaction.getTransactionKey())
                .costumerFullName(transaction.getCard().getAccount().getCustomer().getFullName())
                .costumerId(transaction.getCard().getAccount().getCustomer().getId())
                .build();
    }
}
