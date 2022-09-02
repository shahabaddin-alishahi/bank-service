package energizeglobalservices.bankservice.config.exception;

public class InvalidTokenException extends SecurityException {
    {
        super.logStackTrace = ExceptionSetting.StackTraceDecision.FIRST;
    }
}
