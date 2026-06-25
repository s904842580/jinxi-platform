package com.jinxi.platform.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public final class JwtUtil {

    // 从配置文件读取密钥
    @Value("${jwt.secret}")
    private String secret;

    // 从配置文件读取过期时间（毫秒）
    @Value("${jwt.expiration:86400000}") // 默认 1 天
    private Long expiration;

    // 缓存 Secretkey
    private SecretKey key;
    /**
     * 在 Bean 初始化完成后执行（此时 secret 已注入完毕）
     * 只生成一次 Key，后续方法复用
     */
    @PostConstruct
    public void init() {
        // 提前检查 secret 是否为空，避免启动后才发现配置缺失
        if (secret == null || secret.length() < 32) {
            throw new IllegalStateException("JWT secret must be at least 32 characters");
        }
        //生成安全密钥（要求至少 256 位，即 32 字节）
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    /**
     * 生成 Token
     * @param userId 用户 ID
     * @param username 用户名
     * @return JWT Token
     */
    public String generateToken(Long userId, String username) {
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .id(UUID.randomUUID().toString())// JWT ID，唯一标识
                .subject(String.valueOf(userId))// 主题，通常放用户名
                .claim("userId", userId)// 自定义字段
                .claim("username", username)// 自定义字段
                .issuedAt(now)// 签发时间
                .expiration(expireTime)// 过期时间
                .signWith(key)// 签名
                .compact();
    }
    /**
     * 从 Token 中获取 Claims（包含所有信息）
     */
    public  Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    /**
     * 验证 Token 是否有效（不抛异常即为有效）
     */
    public  boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
            // JWT 常见异常：ExpiredJwtException, UnsupportedJwtException, MalformedJwtException
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 从 Token 中获取用户 ID
     */
    public  Long getUserId(String token) {
        Object userId = parseToken(token).get("userId");
        if (userId instanceof Number number) {
            return number.longValue();
        }
        return Long.valueOf(String.valueOf(userId));
    }
    /**
     * 从 Token 中获取用户名
     */
    public  String getUsername(String token) {
        return parseToken(token).get("username", String.class);
    }
    /**
     * 从 Token 中获取 JWT ID (jti)
     */
    public String getJti(String token) {
        return parseToken(token).getId();
    }
}
