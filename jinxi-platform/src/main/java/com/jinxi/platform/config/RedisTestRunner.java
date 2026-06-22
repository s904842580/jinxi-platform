package com.jinxi.platform.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisTestRunner implements CommandLineRunner {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisTestRunner(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void run(String... args) {
        try {
            // 测试连接：存一个键值对，再读取
            String key = "test:connection";
            String value = "Hello Redis!";
            redisTemplate.opsForValue().set(key, value);
            String result = redisTemplate.opsForValue().get(key);
            System.out.println("✅ Redis 连接成功！测试值：" + result);
        } catch (Exception e) {
            System.err.println("❌ Redis 连接失败：" + e.getMessage());
        }
    }
}