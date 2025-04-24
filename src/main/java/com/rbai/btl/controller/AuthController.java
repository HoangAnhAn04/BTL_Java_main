package com.rbai.btl.controller;

import com.rbai.btl.dto.request.LoginRequest;
import com.rbai.btl.dto.request.RegisterRequest;
import com.rbai.btl.dto.response.ApiResponse;
import com.rbai.btl.dto.response.LoginResponse;
import com.rbai.btl.dto.response.RegisterResponse;
import com.rbai.btl.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @RequestBody RegisterRequest request,
            HttpServletResponse response) {
        ApiResponse<RegisterResponse> result = authService.register(request, response);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response) {
        ApiResponse<LoginResponse> result = authService.login(request, response);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request, HttpServletResponse response) {
        ApiResponse<String> result = authService.logout(request, response);
        return ResponseEntity.ok(result);
    }
}
