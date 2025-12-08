package omniaetern.axel.model.constant;

public enum ErrorCode {

    SUCCESS(0, "Success"),

    USER_NOT_FOUND(1001, "User not found"),
    PASSWORD_MISMATCH(1002, "Password mismatch"),
    PERMISSION_NOT_FOUND(1003, "Permission not found"),
    DEPARTMENT_NOT_FOUND(1004, "Department not found"),
    ROLE_NOT_FOUND(1005, "Role not found"),
    ROLE_PERMISSION_RELATION_NOT_FOUND(1006, "Role permission relation not found"),
    USER_ROLE_RELATION_NOT_FOUND(1007, "User role relation not found"),

    SERVER_ERROR(9000, "Server Error")
    ;

    public final int code;
    public final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
