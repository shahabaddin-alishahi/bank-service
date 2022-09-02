package energizeglobalservices.bankservice.controller.atm.internal.token;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class InternalResolveTokenRequest {

    @NotBlank
    private String token;
}
