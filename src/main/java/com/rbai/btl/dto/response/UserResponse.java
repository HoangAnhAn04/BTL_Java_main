package com.rbai.btl.dto.response;

import com.rbai.btl.enums.RoleName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private long id;
    private String fullName;
    private String email;
    private String username;
    private RoleName role;  
}
