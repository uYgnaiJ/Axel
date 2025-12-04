package omniaetern.axel.common.exception;

import omniaetern.axel.model.constant.ErrorCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import omniaetern.axel.common.http.SR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public SR<?> handleBusinessException(BusinessException ex) {
        return SR.error(ex.getErrorCode());
    }

/*    @ExceptionHandler(Exception.class)
    public SR<?> handleOtherExceptions(Exception ex) {
        return SR.error(ErrorCode.SERVER_ERROR, ex.getMessage());
    }*/
}
