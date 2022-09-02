package energizeglobalservices.bankservice.controller.atm.internal.account.balance;


import energizeglobalservices.bankservice.domain.Transaction;
import energizeglobalservices.bankservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class InternalAccountBalanceController {

    private final AccountService accountService;

    @PostMapping(path = "${apis.internal}/bank-accounts/{bankAccountId}/balance")
    public InternalAccountBalanceResponse handle(@PathVariable("bankAccountId") String bankAccountId,
                                                 @RequestBody @Valid InternalAccountBalanceRequest request){
        Transaction transaction = accountService.getBalance(
                Long.valueOf(bankAccountId), request.getFingerPrint());

        return InternalAccountBalanceResponse.builder()
                .balance(transaction.getCard().getAccount().getBalance())
                .accountId(transaction.getCard().getAccount().getId())
                .cardId(transaction.getCard() == null ? transaction.getCard().getId() : null)
                .cardNumber(transaction.getCard() == null ? transaction.getCard().getCardNumber() : StringUtils.EMPTY)
                .transactionKey(transaction.getTransactionKey())
                .costumerFullName(transaction.getCard().getAccount().getCustomer().getFullName())
                .costumerId(transaction.getCard().getAccount().getCustomer().getId())
                .build();
    }
}
