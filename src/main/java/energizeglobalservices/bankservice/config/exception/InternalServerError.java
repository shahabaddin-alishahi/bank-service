package energizeglobalservices.bankservice.config.exception;

public class InternalServerError extends BusinessException {
    {
        super.logStackTrace = ExceptionSetting.StackTraceDecision.FULL;
    }
}
