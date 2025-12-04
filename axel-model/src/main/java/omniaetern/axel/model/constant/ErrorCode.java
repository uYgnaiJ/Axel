package omniaetern.axel.model.constant;

public enum ErrorCode {

    SUCCESS(0, "Success"),

    SERVER_ERROR(9000, "Server Error")
    ;

    public final int code;
    public final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
