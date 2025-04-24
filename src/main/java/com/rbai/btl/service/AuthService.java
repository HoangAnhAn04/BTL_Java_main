package com.rbai.btl.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rbai.btl.dto.request.LoginRequest;
import com.rbai.btl.dto.request.RegisterRequest;
import com.rbai.btl.dto.response.ApiResponse;
import com.rbai.btl.dto.response.LoginResponse;
import com.rbai.btl.dto.response.RegisterResponse;
import com.rbai.btl.entity.User;
import com.rbai.btl.enums.RoleName;
import com.rbai.btl.enums.StatusCode;
import com.rbai.btl.exception.CustomException;
import com.rbai.btl.repository.AuthRepository;
import com.rbai.btl.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RedisTokenService redisTokenService;
    private final JwtUtil jwtUtil;

    public AuthService(
        AuthRepository authRepository,
        PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager,
        RedisTokenService redisTokenService,
        JwtUtil jwtUtil
    ) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.redisTokenService = redisTokenService;
        this.jwtUtil = jwtUtil;
    }

    public ApiResponse<RegisterResponse> register(RegisterRequest registerRequest, HttpServletResponse response) {
        if (authRepository.findByUsernameOrEmail(registerRequest.getUsername(), registerRequest.getEmail()).isPresent()) {
            throw new CustomException(StatusCode.USERNAME_ALREADY_EXISTS);
        }

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new CustomException(StatusCode.PASSWORD_DOES_NOT_MATCH);
        }

        User user = authRepository.save(
            User.builder()
            .fullName(registerRequest.getFullName())
            // .phone(registerRequest.getPhone())
            // .address(registerRequest.getAddress())
            .username(registerRequest.getUsername())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .email(registerRequest.getEmail())
            .role(RoleName.MEMBER)
            .build()
        );

        String token = jwtUtil.generateToken(user.getUsername());
        redisTokenService.storeToken(user.getUsername(), token);
        jwtUtil.setCookie(token, response);

        RegisterResponse registerResponse = RegisterResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .role(user.getRole())
            .token(token) 
            .build();

        return ApiResponse.success(StatusCode.REGISTER_SUCCESS, registerResponse);
    }

    public ApiResponse<LoginResponse> login(LoginRequest loginRequest, HttpServletResponse response) {
        String username = loginRequest.getUsername();

        if (redisTokenService.isLoggedIn(username)) {
            return ApiResponse.error(StatusCode.IS_LOGGED_IN, null);
        }

        User user = authRepository.findByUsername(username)
            .orElseThrow(() -> new CustomException(StatusCode.USERNAME_NOT_FOUND));

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword())
        );

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new CustomException(StatusCode.INVALID_PASSWORD);
        }

        String token = jwtUtil.generateToken(user.getUsername());
        redisTokenService.storeToken(user.getUsername(), token);
        jwtUtil.setCookie(token, response);

        LoginResponse loginResponse = LoginResponse.builder()
            .username(user.getUsername())
            .email(user.getEmail())
            .build();

        return ApiResponse.success(StatusCode.LOGIN_SUCCESS, loginResponse);
    }

    public ApiResponse<String> logout(HttpServletRequest request, HttpServletResponse response) {
        String token = jwtUtil.getTokenFromCookie(request);
        if (token == null) {
            throw new CustomException(StatusCode.TOKEN_INVALID_OR_EXPIRED);
        }

        String username = jwtUtil.extractUsername(token);
        if (!redisTokenService.isLoggedIn(username)) {
            return ApiResponse.error(StatusCode.NOT_LOGGED_IN, null);
        }

        redisTokenService.removeToken(username);
        jwtUtil.deleteCookie(response);
        return ApiResponse.success(StatusCode.LOGOUT_SUCCESS, null);
    }
}