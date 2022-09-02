package energizeglobalservices.bankservice.service.impl;

import energizeglobalservices.bankservice.config.security.JwtTokenProvider;
import energizeglobalservices.bankservice.domain.ATMMachine;
import energizeglobalservices.bankservice.domain.AuthenticationResult;
import energizeglobalservices.bankservice.domain.ProtectedValue;
import energizeglobalservices.bankservice.exception.*;
import energizeglobalservices.bankservice.repository.ATMMachineRepository;
import energizeglobalservices.bankservice.service.ATMMachineService;
import energizeglobalservices.bankservice.service.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class ATMMachineServiceImpl implements ATMMachineService, UserDetailsService {

    private final ATMMachineRepository atmRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorityService authorityService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ATMMachineServiceImpl(ATMMachineRepository atmRepository,
                                 JwtTokenProvider jwtTokenProvider,
                                 AuthorityService authorityService,
                                 @Lazy PasswordEncoder passwordEncoder,
                                 @Lazy AuthenticationManager authenticationManager) {
        this.atmRepository = atmRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ATMMachine findByUserName(String username) {
        return atmRepository.findByUsername(username)
                .orElseThrow(ATMMachineNotFoundException::new);
    }

    @Override
    public ATMMachine findById(String id) {
        return atmRepository.findById(Long.parseLong(id))
                .orElseThrow(ATMMachineNotFoundException::new);
    }

    @Override
    public ATMMachine findById(Long id) {
        return atmRepository.findById(id)
                .orElseThrow(ATMMachineNotFoundException::new);
    }

    @Override
    public AuthenticationResult login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            ATMMachine atmMachine = findByUserName(username);
            return jwtTokenProvider.generateToken(atmMachine);
        } catch (DisabledException e) {
            throw new ATMMachineAccountDisabledException();
        } catch (BadCredentialsException e) {
            throw new BadCredentialException();
        }
    }

    @Override
    public AuthenticationResult refreshAccessToken(String refreshToken) {
        return jwtTokenProvider.refreshToken(refreshToken);
    }

    @Override
    public ATMMachine loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.findByUserName(username);
    }


    @Override
    public void changePassword(Long id, ProtectedValue oldPassword, ProtectedValue newPassword, ProtectedValue confirmPassword) {
        ATMMachine atmMachine = findById(id);

        if (!passwordEncoder.matches(oldPassword.getValue(), atmMachine.getPassword())) {
            throw new InvalidOldPasswordException();
        }

        if (!StringUtils.equals(newPassword.getValue(), confirmPassword.getValue())) {
            throw new NewPasswordAndConfirmPasswordNotSameException();
        }

        atmMachine.setPassword(passwordEncoder.encode(newPassword.getValue()));
    }

}
