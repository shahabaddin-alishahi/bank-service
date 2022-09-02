package energizeglobalservices.bankservice.controller.atm.internal.card.balance;


import energizeglobalservices.bankservice.domain.Transaction;
import energizeglobalservices.bankservice.service.AccountService;
import energizeglobalservices.bankservice.service.CardService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class InternalCardBalanceController {

    private final CardService cardService;

    @PostMapping(path = "${apis.internal}/cards/{cardId}/balance")
    public InternalCardBalanceResponse handle(@PathVariable("cardId") String cardId,
                                              @RequestBody InternalCardBalanceRequest request){
        Transaction transaction = cardService.getAccountBalance(Long.valueOf(cardId),
                request.getPin1(),
                request.getPin1Check() !=null ? request.getPin1Check() : false,
                request.getFingerPrint(),
                request.getFingerPrintCheck() != null ? request.getFingerPrintCheck() : false);

        return InternalCardBalanceResponse.builder()
                .balance(transaction.getCard().getAccount().getBalance())
                .accountId(transaction.getCard().getAccount().getId())
                .cardId(transaction.getCard().getId())
                .cardNumber(transaction.getCard().getCardNumber())
                .transactionKey(transaction.getTransactionKey())
                .costumerFullName(transaction.getCard().getAccount().getCustomer().getFullName())
                .costumerId(transaction.getCard().getAccount().getCustomer().getId())
                .build();
    }
}
