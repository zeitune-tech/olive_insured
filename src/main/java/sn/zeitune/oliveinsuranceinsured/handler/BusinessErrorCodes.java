package sn.zeitune.oliveinsuranceinsured.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessErrorCodes {

    NO_CODE(0, "No code", HttpStatus.NOT_IMPLEMENTED),
    INCORRECT_CURRENT_PASSWORD(300, "Incorrect current password", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_MISMATCH(301, "New password and confirm password do not match", HttpStatus.BAD_REQUEST),
    ACCOUNT_LOCKED(302, "User account is locked", HttpStatus.FORBIDDEN),
    ACCOUNT_DISABLED(303, "User account is disabled", HttpStatus.FORBIDDEN),
    BAD_CREDENTIALS(304, "Bad credentials", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND(305, "User not found", HttpStatus.NOT_FOUND),
    DUPLICATE_EMAIL(306, "Email already exists", HttpStatus.BAD_REQUEST),
    DUPLICATE_USERNAME(307, "Username already exists", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String description;
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}