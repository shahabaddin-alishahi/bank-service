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
@Entity(name = "Card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Account account;

    @NonNull
    private String cardNumber;

    @NonNull
    private String pin1;

    private String pin2;

    private int failureRetryCount;

    private int cvv2;

    private int expireDate;

    private boolean isEnabled;

    private boolean suspended;

    private boolean blocked;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

}
