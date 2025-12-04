package omniaetern.axel.common.exception;

import omniaetern.axel.model.constant.ErrorCode;

public class BusinessException extends RuntimeException{
    public final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.message);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
