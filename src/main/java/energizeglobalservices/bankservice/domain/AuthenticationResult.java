package energizeglobalservices.bankservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResult {
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpirationTimeInMilliSeconds;
    private Long refreshTokenExpirationTimeInMilliSeconds;
}
