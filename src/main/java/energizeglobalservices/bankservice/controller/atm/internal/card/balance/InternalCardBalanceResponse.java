package energizeglobalservices.bankservice.controller.atm.internal.card.balance;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class InternalCardBalanceResponse {

    private Double balance;
//    Double totalBalance;
//    Double freezeAmount;
//    Double availableBalance;

    private Long accountId;
    private Long cardId;
    private String cardNumber;
    private String transactionKey;
    private Long costumerId;
    private String costumerFullName;
}
