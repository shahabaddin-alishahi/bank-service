package energizeglobalservices.bankservice.domain;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean fingerPrintEnabled;

    @NonNull
    private boolean isActive;

    @NonNull
    private String fingerPrint;

    @NonNull
    private String nationalCode;

    @NonNull
    private String mobileNumber;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @Transient
    @Getter(value = AccessLevel.NONE)
    private String fullName;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
