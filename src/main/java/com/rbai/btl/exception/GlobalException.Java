package com.rbai.btl.exception;

import com.rbai.btl.enums.StatusCode;
import com.rbai.btl.dto.response.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ===== CustomException =====
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomException(CustomException ex) {
        StatusCode errorCode = ex.getStatusCode();
        ApiResponse<Object> errorResponse = ApiResponse.error(
            errorCode.getCode(),
            errorCode.getDetails(),
            errorCode.getMeta(),
            List.of()
        );
        return ResponseEntity.status(errorCode.getCode()).body(errorResponse);
    }

    // ===== Generic Exception =====
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Đã có lỗi xảy ra, vui lòng thử lại sau",
                List.of(ex.getMessage())
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
