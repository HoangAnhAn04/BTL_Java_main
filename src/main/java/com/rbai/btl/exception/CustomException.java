package com.rbai.btl.exception;

import com.rbai.btl.enums.StatusCode;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final StatusCode statusCode;

    public CustomException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
    }
}
