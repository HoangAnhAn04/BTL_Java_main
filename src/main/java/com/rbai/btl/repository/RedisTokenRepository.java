package com.rbai.btl.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rbai.btl.entity.RedisToken;

@Repository
public interface RedisTokenRepository extends CrudRepository<RedisToken, String> {
    RedisToken findByUsername(String username);
    
    void deleteByToken(String token);
}
