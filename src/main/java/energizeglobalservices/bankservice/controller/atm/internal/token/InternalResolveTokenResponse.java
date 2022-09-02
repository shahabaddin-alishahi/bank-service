package energizeglobalservices.bankservice.controller.atm.internal.token;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InternalResolveTokenResponse {

    private Long id;
    private String username;
    private Boolean isEnabled;
    private String ipAddress;
    private String atmModel;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private List<String> authorities;
}
