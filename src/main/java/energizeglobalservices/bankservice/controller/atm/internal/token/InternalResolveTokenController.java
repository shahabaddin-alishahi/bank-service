package energizeglobalservices.bankservice.controller.atm.internal.token;

import energizeglobalservices.bankservice.config.exception.InvalidTokenException;
import energizeglobalservices.bankservice.config.exception.TokenExpiredException;
import energizeglobalservices.bankservice.config.security.JwtTokenProvider;
import energizeglobalservices.bankservice.config.security.OnlineATM;
import energizeglobalservices.bankservice.config.security.OnlineATMImpl;
import energizeglobalservices.bankservice.controller.atm.internal.card.withdrawal.InternalCardWithdrawalResponse;
import energizeglobalservices.bankservice.controller.atm.internal.card.withdrawal.InternalWithdrawAccountRequest;
import energizeglobalservices.bankservice.domain.ATMMachine;
import energizeglobalservices.bankservice.domain.Transaction;
import energizeglobalservices.bankservice.service.ATMMachineService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class InternalResolveTokenController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ATMMachineService atmMachineService;

    @PostMapping(path = "${apis.internal}/tokens/resolve")
    public InternalResolveTokenResponse handle(
            @RequestBody @Valid InternalResolveTokenRequest request) {

        String username = jwtTokenProvider.getUsernameFromToken(request.getToken());
        if (username != null) {
            ATMMachine atmMachine = atmMachineService.loadUserByUsername(username);
            OnlineATM onlineATM = new OnlineATMImpl(atmMachine.getId(), atmMachine.getUsername(),
                    atmMachine.getPassword(), atmMachine.getAuthorities());

            if (jwtTokenProvider.validateToken(request.getToken(), atmMachine)) {

                return InternalResolveTokenResponse.builder()
                        .id(onlineATM.getId())
                        .username(onlineATM.getUsername())
                        .atmModel(atmMachine.getAtmModel())
                        .createDate(atmMachine.getCreateDate())
                        .updateDate(atmMachine.getUpdateDate())
                        .ipAddress(atmMachine.getIpAddress())
                        .authorities(onlineATM.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority
                        ).collect(Collectors.toList()))
                        .build();
            }
        } else {
            throw new InvalidTokenException();
        }
        throw new InvalidTokenException();
    }
}
