package energizeglobalservices.bankservice.config.exception;

public class TokenExpiredException extends SecurityException {
    {
        super.logStackTrace = ExceptionSetting.StackTraceDecision.PARTIAL;
    }
}
