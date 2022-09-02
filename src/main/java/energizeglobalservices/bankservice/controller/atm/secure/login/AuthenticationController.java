package energizeglobalservices.bankservice.controller.atm.secure.login;

import energizeglobalservices.bankservice.domain.AuthenticationResult;
import energizeglobalservices.bankservice.service.ATMMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final ATMMachineService atmMachineService;

    @PostMapping("${apis.secure}/login")
    public AuthenticationResponse handle(@RequestBody @Valid AuthenticationRequest request){

        AuthenticationResult tokens = atmMachineService.login(request.getUsername(), request.getPassword());

        return AuthenticationResponse.builder()
                .accessToken(tokens.getAccessToken())
                .refreshToken(tokens.getRefreshToken())
                .accessTokenExpirationTimeInMilliSeconds(tokens.getAccessTokenExpirationTimeInMilliSeconds())
                .refreshTokenExpirationTimeInMilliSeconds(tokens.getRefreshTokenExpirationTimeInMilliSeconds())
                .build();
    }
}
