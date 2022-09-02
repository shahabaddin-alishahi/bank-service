package energizeglobalservices.bankservice.controller.atm.internal.card.withdrawal;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class InternalCardWithdrawalResponse {

    private Double balance;
    private Double amount;
    private Long accountId;
    private Long cardId;
    private String cardNumber;
    private String transactionKey;
    private Long costumerId;
    private String costumerFullName;
}
