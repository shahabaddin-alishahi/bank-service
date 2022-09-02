package energizeglobalservices.bankservice.controller.atm.internal.card.deposite;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class InternalCardDepositResponse {

    private Double balance;
    private Double amount;
    private Long accountId;
    private Long cardId;
    private String cardNumber;
    private String transactionKey;
    private Long costumerId;
    private String costumerFullName;

}
