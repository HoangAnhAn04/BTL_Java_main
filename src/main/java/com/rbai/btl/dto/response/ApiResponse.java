package com.rbai.btl.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rbai.btl.enums.StatusCode;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private Integer code;
    private String message;
    private T data;
    private Object details;
    private Object meta;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant timestamp = Instant.now();

    public ApiResponse(boolean success, Integer code, String message, T data, Object details, Object meta) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.details = details;
        this.meta = meta;
    }

    // ===== Success =====
    public static <T> ApiResponse<T> success(StatusCode code) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), null, null, null);
    }

    public static <T> ApiResponse<T> success(StatusCode code, T data) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), data, null, null);
    }

    public static <T> ApiResponse<T> success(StatusCode code, T data, Object meta) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), data, null, meta);
    }

    // ===== Error =====
    public static <T> ApiResponse<T> error(StatusCode code) {
        return new ApiResponse<>(false, code.getCode(), code.getMessage(), null, null, null);
    }

    public static <T> ApiResponse<T> error(StatusCode code, Object details) {
        return new ApiResponse<>(false, code.getCode(), code.getMessage(), null, details, null);
    }

    public static <T> ApiResponse<T> error(StatusCode code, Object details, Object meta) {
        return new ApiResponse<>(false, code.getCode(), code.getMessage(), null, details, meta);
    }
}
