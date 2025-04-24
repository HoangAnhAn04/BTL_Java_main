package com.rbai.btl.enums;

import lombok.Getter;

@Getter
public enum StatusCode {
    // Default status code
    SUCCESS(200, "Success"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    VALIDATION_ERROR(422, "Validation Error"),
    
    // Custom status codes
    TOKEN_INVALID_OR_EXPIRED(1001, "Token is invalid or expired"),
    USERNAME_ALREADY_EXISTS(1002, "Username already exists"),
    EMAIL_ALREADY_EXISTS(1003, "Email already exists"),
    PASSWORD_DOES_NOT_MATCH(1004, "Password does not match"),
    REGISTER_SUCCESS(1005, "Register success"),
    USERNAME_NOT_FOUND(1006, "Username not found"),
    LOGIN_SUCCESS(1007, "Login success"),
    INVALID_PASSWORD(1008, "Invalid password"),
    IS_LOGGED_IN(1009, "User is already logged in"),
    LOGOUT_SUCCESS(1010, "Logout success"),
    NOT_LOGGED_IN(1011, "User is not logged in"),
    ;

    private final int code;
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
