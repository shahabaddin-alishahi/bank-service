package energizeglobalservices.bankservice.controller.atm.internal.card.validation;

import energizeglobalservices.bankservice.domain.Account;
import energizeglobalservices.bankservice.domain.Customer;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class InternalCardValidationResponse {

    private Double balance;
//    Double totalBalance;
//    Double freezeAmount;
//    Double availableBalance;

    private Long accountId;
    private Long cardId;
    private String cardNumber;
    private Long costumerId;
    private String costumerFullName;
    private int failureRetryCount;
    private boolean suspended;
    private boolean blocked;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
