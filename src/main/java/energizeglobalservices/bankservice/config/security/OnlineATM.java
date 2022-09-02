package energizeglobalservices.bankservice.config.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface OnlineATM extends UserDetails {

    Long getId();

}
