package energizeglobalservices.bankservice.controller.atm.internal.card.balance;

import lombok.Getter;

@Getter
public class InternalCardBalanceRequest {

    private Boolean pin1Check;
    private String pin1;
    private String fingerPrint;
    private Boolean fingerPrintCheck;

}
