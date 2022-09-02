package energizeglobalservices.bankservice.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "Account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    private int failureRetryCount;


    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Set<Card> cards;

    private boolean isEnabled;

    private Double balance;

    private LocalDateTime registrationDate;

    private LocalDateTime updateDate;

}
