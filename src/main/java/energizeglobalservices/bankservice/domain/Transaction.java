package energizeglobalservices.bankservice.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "Transaction")
public class Transaction {


    public enum TransactionType {
        CASH_DEPOSIT,
        CASH_WITHDRAW,
        TRANSFER_AMOUNT,
        CHECK_BALANCE
    }

    public enum TransactionStatus {
        SUCCEED, FAILED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Card card;

    @Enumerated(value = EnumType.STRING)
    private TransactionType requestType;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus statusType;

    private String sourceCardNumber;

    private String destinationCardNumber;

    @NonNull
    private String transactionKey;

    private Double amount;

    private LocalDateTime createDate;

}
