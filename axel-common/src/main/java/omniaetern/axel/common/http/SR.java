package omniaetern.axel.common.http;

import omniaetern.axel.model.constant.ErrorCode;

public class SR<T> extends StandardResponse<T> {
    public SR() {}

    public SR(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static SR<?> success() {return new SR<>();}

    public static <T> SR<T> success(T data) {
        return new SR<>(ErrorCode.SUCCESS.code, ErrorCode.SUCCESS.message, data);
    }

    public static SR<?> error(ErrorCode errorCode) {
        return new SR<>(errorCode.code, errorCode.message, null);
    }

    public static SR<?> error(ErrorCode errorCode, String message) {
        return new SR<>(errorCode.code, errorCode.message + " " + message, null);
    }

    public static <T> SR<T> error(ErrorCode errorCode, String message, T data) {
        return new SR<>(errorCode.code, errorCode.message + " " + message, data);
    }
}