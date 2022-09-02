package energizeglobalservices.bankservice.config.security;

import energizeglobalservices.bankservice.domain.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class OnlineATMImpl implements OnlineATM {

    private Long userId;
    private String username;
    private String password;
    private Collection<Authority> authorities;

    public OnlineATMImpl(long userId, String username, String password, Set<Authority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        authorities.forEach(
                authority -> grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()))
        );

        return grantedAuthorities;
    }

    @Override
    public Long getId() {
        return userId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
