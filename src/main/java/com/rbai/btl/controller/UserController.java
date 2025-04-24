package com.rbai.btl.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbai.btl.dto.request.UserCreateRequest;
import com.rbai.btl.dto.response.ApiResponse;
import com.rbai.btl.dto.response.UserResponse;
import com.rbai.btl.service.AuthService;
import com.rbai.btl.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping()
    public ApiResponse<List<UserResponse>> getAll() {
        return userService.getAll();
    }
    
    @GetMapping("/{username}")
    public ApiResponse<UserResponse> getByUsername(@PathVariable String username) {
        return userService.getByUsername(username);
    }
    
    @PostMapping("/create")
        public ResponseEntity<ApiResponse<String>> create(
            @RequestBody UserCreateRequest request) {
        ApiResponse<String> result = userService.create(request);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponse<String>> update(
        @RequestBody Map<String, Object> update, @PathVariable Long id) {
        ApiResponse<String> result = userService.update(id, update);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        ApiResponse<String> result = userService.delete(id);
        authService.logout(request, response);
        return ResponseEntity.ok(result);
    }
}
