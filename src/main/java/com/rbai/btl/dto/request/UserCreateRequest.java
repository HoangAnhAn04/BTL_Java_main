package com.rbai.btl.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {

    private String fullName;
    private String email;
    private String username;
    private String password;
    private String role;
}