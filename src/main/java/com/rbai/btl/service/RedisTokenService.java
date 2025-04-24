package com.rbai.btl.service;

import com.rbai.btl.entity.RedisToken;
import com.rbai.btl.repository.RedisTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class RedisTokenService {
    private final RedisTokenRepository redisTokenRepository;

    public RedisTokenService(RedisTokenRepository redisTokenRepository) {
        this.redisTokenRepository = redisTokenRepository;
    }

    public RedisToken extractUsername(String username) {
        return redisTokenRepository.findById(username).orElse(null);
    }

    public void storeToken(String username, String token) {
        redisTokenRepository.save(new RedisToken(username, token));
    }

    public boolean isTokenValid(String username, String token) {
        return redisTokenRepository.findById(username)
                .map(redisToken -> redisToken.getToken().equals(token))
                .orElse(false);
    }

    public void removeToken(String username) {
        redisTokenRepository.deleteById(username);
    }

    public void removeTokenByValue(String token) {
        redisTokenRepository.deleteByToken(token);
    }

    public boolean isLoggedIn(String username) {
        if (username == null) {
            return false;
        }
        return redisTokenRepository.existsById(username);
    }
}
