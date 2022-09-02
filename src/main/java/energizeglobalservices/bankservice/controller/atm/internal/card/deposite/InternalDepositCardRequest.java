package energizeglobalservices.bankservice.controller.atm.internal.card.deposite;

import lombok.Getter;

@Getter
public class InternalDepositCardRequest {

    private double amount;
    private Boolean pin1Check;
    private String pin1;
    private String fingerPrint;
    private Boolean fingerPrintCheck;

}
