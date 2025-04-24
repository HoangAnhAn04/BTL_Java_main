package com.rbai.btl.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String fullName;
    // private String phone;
    // private String address;
    private String email;
    private String username;
    private String password;
    private String confirmPassword;
    private String role;
}
