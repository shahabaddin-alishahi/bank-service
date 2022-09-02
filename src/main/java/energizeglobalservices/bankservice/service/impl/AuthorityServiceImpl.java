package energizeglobalservices.bankservice.service.impl;

import energizeglobalservices.bankservice.domain.Authority;
import energizeglobalservices.bankservice.exception.AuthorityNotFoundException;
import energizeglobalservices.bankservice.repository.AuthorityRepository;
import energizeglobalservices.bankservice.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    public List<Authority> getListOfAllAuthorities() {
        return authorityRepository.findAll();
    }

    @Override
    public Authority findById(String id) {
        return authorityRepository.findById(Long.parseLong(id))
                .orElseThrow(AuthorityNotFoundException::new);
    }

    @Override
    public Authority createAuthority(String title, String description) {
        return authorityRepository.save(Authority.builder()
                .description(description)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .title(title)
                .build());
    }

    @Override
    public Authority updateAuthority(String authorityId, String title, String description) {

        Authority authority = this.findById(authorityId);

        if (Objects.nonNull(title))
            authority.setTitle(title);

        if (Objects.nonNull(description))
            authority.setDescription(description);

        authority.setUpdateDate(LocalDateTime.now());

        return authority;
    }
}
