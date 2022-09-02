package energizeglobalservices.bankservice.controller.atm.internal.card.withdrawal;

import lombok.Getter;

@Getter
public class InternalWithdrawAccountRequest {

    private double amount;
    private Boolean pin1Check;
    private String pin1;
    private String fingerPrint;
    private Boolean fingerPrintCheck;
}
