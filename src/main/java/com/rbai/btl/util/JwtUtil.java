package com.rbai.btl.util;

import com.rbai.btl.config.JwtConfig;
import com.rbai.btl.enums.StatusCode;
import com.rbai.btl.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final JwtConfig jwtConfig;

    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Value("${jwt.secret}")
    private String secretKeyBase64;

    @Value("${jwt.expiration-ms:86400000}")
    private long expirationTime;

    @Value("${jwt.cookie-name:session_token}")
    private String cookieName;

    @Value("${server.secure-cookie:true}")
    private boolean secureCookie;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecret()));
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        try {
            return getClaims(token).getSubject();
        } catch (CustomException e) {
            return null;
        }
    }

    public boolean validateToken(String token, String username) {
        try {
            Claims claims = getClaims(token);
            return username.equals(claims.getSubject()) && !claims.getExpiration().before(new Date());
        } catch (CustomException e) {
            return false;
        }
    }

    public void setCookie(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge((int) (expirationTime / 1000));

        response.addCookie(cookie);
    }

    public void deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(secureCookie);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }

    public String getTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            throw new CustomException(StatusCode.TOKEN_INVALID_OR_EXPIRED);
        } catch (Exception e) {
            throw new CustomException(StatusCode.TOKEN_INVALID_OR_EXPIRED);
        }
    }
}
