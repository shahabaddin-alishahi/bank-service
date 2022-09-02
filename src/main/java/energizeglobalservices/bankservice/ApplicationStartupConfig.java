package energizeglobalservices.bankservice;

import energizeglobalservices.bankservice.domain.ATMMachine;
import energizeglobalservices.bankservice.domain.Authority;
import energizeglobalservices.bankservice.repository.ATMMachineRepository;
import energizeglobalservices.bankservice.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ApplicationStartupConfig {

    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final ATMMachineRepository atmMachineRepository;


    @Bean
    public void populateDatabaseWithAdminUser() {
        Set<Authority> authoritySet = new HashSet<>(authorityRepository.findAll());

        atmMachineRepository.findByUsername("admin").ifPresentOrElse(
                admin -> {
                    if (admin.getAuthorities().size() != authoritySet.size()) {
                        admin.setAuthorities(authoritySet);
                        atmMachineRepository.save(admin);
                    }
                },
                () -> {
                    String password = RandomStringUtils.randomAlphanumeric(20);

                    atmMachineRepository.save(ATMMachine.builder()
                            .id(1L)
                            .username("admin")
                            .password(passwordEncoder.encode(password))
                            .isEnabled(true)
                            .atmModel("ABC")
                            .ipAddress("192.168.0.100")
                            .createDate(LocalDateTime.now())
                            .updateDate(LocalDateTime.now())
                            .authorities(authoritySet)
                            .build());

                    log.info("\n Admin account's password is set to : {} \n", password);
                }
        );
    }
}
