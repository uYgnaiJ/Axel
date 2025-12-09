package omniaetern.axel.model.constant;

public enum ErrorCode {

    SUCCESS(0, "Success"),


    USER_NOT_FOUND(1010, "User not found"),
    PASSWORD_MISMATCH(1011, "Password mismatch"),
    USER_ROLE_RELATION_NOT_FOUND(1012, "User role relation not found"),
    USERNAME_DUPLICATE(1013, "Username already exists"),

    DEPARTMENT_NOT_FOUND(1020, "Department not found"),

    PERMISSION_NOT_FOUND(1030, "Permission not found"),

    ROLE_NOT_FOUND(1040, "Role not found"),
    ROLE_PERMISSION_RELATION_NOT_FOUND(1041, "Role permission relation not found"),


    LOGIN_FAILURE(1101, "Login failure"),


    SERVER_ERROR(9000, "Server Error")
    ;

    public final int code;
    public final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
