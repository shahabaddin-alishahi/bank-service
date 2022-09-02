package energizeglobalservices.bankservice.service;

import energizeglobalservices.bankservice.domain.Authority;

import java.util.List;

public interface AuthorityService {

    List<Authority> getListOfAllAuthorities();

    Authority findById(String id);

    Authority createAuthority(String title, String description);

    Authority updateAuthority(String authorityId, String title, String description);
}
