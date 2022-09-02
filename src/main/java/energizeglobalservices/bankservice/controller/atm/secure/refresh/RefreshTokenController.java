package energizeglobalservices.bankservice.controller.atm.secure.refresh;

import energizeglobalservices.bankservice.domain.AuthenticationResult;
import energizeglobalservices.bankservice.service.ATMMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class RefreshTokenController {

    private final ATMMachineService atmMachineService;

    @PostMapping("${apis.secure}/refresh")
    public RefreshTokenResponse handle(@RequestBody @Valid RefreshTokenRequest request){
        AuthenticationResult tokens = atmMachineService.refreshAccessToken(request.refreshToken);
        return RefreshTokenResponse.builder()
                .accessToken(tokens.getAccessToken())
                .accessTokenExpirationTimeInMilliSeconds(tokens.getAccessTokenExpirationTimeInMilliSeconds())
                .refreshToken(tokens.getRefreshToken())
                .refreshTokenExpirationTimeInMilliSeconds(tokens.getRefreshTokenExpirationTimeInMilliSeconds())
                .build();
    }
}
