package energizeglobalservices.bankservice.config.exception;

public class NotAcceptableException extends BusinessException {
    {
        super.logStackTrace = ExceptionSetting.StackTraceDecision.FIRST;
    }
}
