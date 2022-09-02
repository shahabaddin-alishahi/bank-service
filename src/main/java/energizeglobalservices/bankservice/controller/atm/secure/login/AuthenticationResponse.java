package energizeglobalservices.bankservice.controller.atm.secure.login;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class AuthenticationResponse {

    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpirationTimeInMilliSeconds;
    private Long refreshTokenExpirationTimeInMilliSeconds;
}
