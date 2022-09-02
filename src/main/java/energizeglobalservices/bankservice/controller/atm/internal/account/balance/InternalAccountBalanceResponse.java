package energizeglobalservices.bankservice.controller.atm.internal.account.balance;

import lombok.*;

@Getter
@Setter
@Builder
public class InternalAccountBalanceResponse {

    private Double balance;
    private Long accountId;
    private Long cardId;
    private String cardNumber;
    private String transactionKey;
    private Long costumerId;
    private String costumerFullName;
}
