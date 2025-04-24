package com.rbai.btl.service;

import com.rbai.btl.entity.User;
import com.rbai.btl.repository.AuthRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> userOptional = authRepository.findByUsername(usernameOrEmail);
        
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Người dùng không tồn tại"));

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())))
            .build();
    }
}
