package energizeglobalservices.bankservice.service;

import energizeglobalservices.bankservice.domain.ATMMachine;
import energizeglobalservices.bankservice.domain.AuthenticationResult;
import energizeglobalservices.bankservice.domain.ProtectedValue;

public interface ATMMachineService {

    ATMMachine findByUserName(String username);

    ATMMachine findById(String id);

    ATMMachine findById(Long id);

    AuthenticationResult login(String username, String password);

    AuthenticationResult refreshAccessToken(String refreshToken);

    ATMMachine loadUserByUsername(String username);

    void changePassword(Long id, ProtectedValue oldPassword, ProtectedValue newPassword, ProtectedValue confirmPassword);
}
