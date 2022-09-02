package energizeglobalservices.bankservice.controller.atm.internal.card.deposite;

import energizeglobalservices.bankservice.domain.Transaction;
import energizeglobalservices.bankservice.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class InternalCardDepositController {

    private final CardService cardService;

    @PostMapping(path = "${apis.internal}/cards/{cardId}/deposit")
    public InternalCardDepositResponse handle(@PathVariable("cardId") String cardId,
                                              @RequestBody @Valid InternalDepositCardRequest request){

        Transaction transaction = cardService.depositOrWithdrawalAmount(Long.valueOf(cardId), request.getPin1(),
                request.getPin1Check() !=null ? request.getPin1Check() : false,
                request.getFingerPrint(),
                request.getFingerPrintCheck() != null ? request.getFingerPrintCheck() : false,
                request.getAmount(), Transaction.TransactionType.CASH_DEPOSIT);

        return InternalCardDepositResponse.builder()
                .balance(transaction.getCard().getAccount().getBalance())
                .amount(transaction.getAmount())
                .accountId(transaction.getCard().getAccount().getId())
                .cardId(Long.valueOf(cardId))
                .cardNumber(transaction.getCard().getCardNumber())
                .transactionKey(transaction.getTransactionKey())
                .costumerFullName(transaction.getCard().getAccount().getCustomer().getFullName())
                .costumerId(transaction.getCard().getAccount().getCustomer().getId())
                .build();
    }
}
