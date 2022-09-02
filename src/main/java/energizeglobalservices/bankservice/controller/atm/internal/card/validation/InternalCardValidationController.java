package energizeglobalservices.bankservice.controller.atm.internal.card.validation;


import energizeglobalservices.bankservice.domain.Card;
import energizeglobalservices.bankservice.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class InternalCardValidationController {

    private final CardService cardService;

    @GetMapping(path = "${apis.internal}/cards/{cardNumber}/validation")
    public InternalCardValidationResponse handle(@PathVariable("cardNumber") String cardNumber){
        Card card = cardService.getCard(cardNumber);

        return InternalCardValidationResponse.builder()
                .balance(card.getAccount().getBalance())
                .accountId(card.getAccount().getId())
                .cardId(card.getId())
                .cardNumber(card.getCardNumber())
                .costumerFullName(card.getAccount().getCustomer().getFullName())
                .costumerId(card.getAccount().getCustomer().getId())
                .blocked(card.isBlocked())
                .suspended(card.isSuspended())
                .failureRetryCount(card.getFailureRetryCount())
                .createDate(card.getCreateDate())
                .updateDate(card.getUpdateDate())
                .build();
    }
}
