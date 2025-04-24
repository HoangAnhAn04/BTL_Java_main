package com.rbai.btl.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rbai.btl.dto.request.UserCreateRequest;
import com.rbai.btl.dto.response.ApiResponse;
import com.rbai.btl.dto.response.UserResponse;
import com.rbai.btl.entity.User;
import com.rbai.btl.enums.RoleName;
import com.rbai.btl.enums.StatusCode;
import com.rbai.btl.exception.CustomException;
import com.rbai.btl.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ApiResponse<List<UserResponse>> getAll() {
        List<UserResponse> users = userRepository.findAll().stream()
            .map(this::convertToUserResponse)
            .collect(Collectors.toList());
        return ApiResponse.success(StatusCode.SUCCESS, users);
    }

    public ApiResponse<UserResponse> getByUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new CustomException(StatusCode.USERNAME_NOT_FOUND));
        return ApiResponse.success(StatusCode.SUCCESS, convertToUserResponse(user));
    }

    @Transactional
    public ApiResponse<String> create(UserCreateRequest request) {
        if (userRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail()).isPresent()) {
            throw new CustomException(StatusCode.USERNAME_ALREADY_EXISTS);
        }

        userRepository.save(
            User.builder()
            .fullName(request.getFullName())
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .email(request.getEmail())
            .role(RoleName.valueOf(request.getRole()))
            .build()
        );

        return ApiResponse.success(StatusCode.SUCCESS);
    }

    @Transactional
    public ApiResponse<String> update(Long id, Map<String, Object> update) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new CustomException(StatusCode.USERNAME_NOT_FOUND));

        String currentUsername = getCurrentUsername();

        // Chỉ cho phép ADMIN hoặc chính chủ
        if (!currentUsername.equals(user.getUsername()) && 
            !userRepository.findByUsername(currentUsername).get().getRole().equals(RoleName.ADMIN)) {
            throw new CustomException(StatusCode.UNAUTHORIZED);
        }

        BeanWrapper beanWrapper = new BeanWrapperImpl(user);
        update.forEach((key, value) -> {
            if (beanWrapper.isWritableProperty(key) && value != null) {
                beanWrapper.setPropertyValue(key, value);
            }
        });

        return ApiResponse.success(StatusCode.SUCCESS);
    }


    @Transactional
    public ApiResponse<String> delete(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new CustomException(StatusCode.USERNAME_NOT_FOUND));
    
        String currentUsername = getCurrentUsername();
    
        // Chỉ cho phép ADMIN hoặc chính chủ
        if (!currentUsername.equals(user.getUsername()) && 
            !userRepository.findByUsername(currentUsername).get().getRole().equals(RoleName.ADMIN)) {
            throw new CustomException(StatusCode.UNAUTHORIZED);
        }
    
        userRepository.delete(user);
        return ApiResponse.success(StatusCode.SUCCESS);
    }
    
    private UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .fullName(user.getFullName())
            .email(user.getEmail())
            .username(user.getUsername())
            .role(user.getRole())
            .build();
    }
}
