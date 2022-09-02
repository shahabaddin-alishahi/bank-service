package energizeglobalservices.bankservice.controller.atm.secure.password;


import energizeglobalservices.bankservice.config.security.OnlineATM;
import energizeglobalservices.bankservice.domain.GeneralStatus;
import energizeglobalservices.bankservice.domain.ProtectedValue;
import energizeglobalservices.bankservice.service.ATMMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@PreAuthorize("hasAuthority('ATM_CHANGE_PASSWORD')")
@RestController
@RequiredArgsConstructor
public class ChangeUserPasswordController {

    private final ATMMachineService atmMachineService;
    private final OnlineATM onlineATM;

    @PutMapping("${apis.secure}/atm/change-password")
    public GeneralStatus handle(@Valid @RequestBody ChangePasswordRequest request) {
        atmMachineService.changePassword(onlineATM.getId(),
                ProtectedValue.builder().value(request.getOldPassword()).build(),
                ProtectedValue.builder().value(request.getNewPassword()).build(),
                ProtectedValue.builder().value(request.getConfirmPassword()).build());

        return GeneralStatus.builder()
                .status(GeneralStatus.Status.SUCCESS)
                .build();
    }
}
