//package com.grigoriy.repository.impl.redis;
//
//import com.grigoriy.repository.RateLimiter;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class RateLimiterImpl implements RateLimiter {
//
//    private final StringRedisTemplate redisTemplate;
//    private final String KEY_PREFIX = "user-processing-rate-limit:"; // Add prefix for clarity
//    private final long LIMIT_INTERVAL_SECONDS = 1;
//    private final long MAX_REQUESTS_PER_INTERVAL = 3;  //  Установите лимит
//
//    public RateLimiterImpl(StringRedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    @Override
//    public boolean allowRequest() {
//        String key = KEY_PREFIX + System.currentTimeMillis() / 1000 / LIMIT_INTERVAL_SECONDS;
//        Long count = redisTemplate.opsForValue().increment(key);
//
//        if (count == null) {
//            return false; // Should not happen, but handle it
//        }
//
//        if (count == 1) {
//            // Set expiration only when the key is first created
//            redisTemplate.expire(key, LIMIT_INTERVAL_SECONDS, TimeUnit.SECONDS);
//        }
//
//        return count <= MAX_REQUESTS_PER_INTERVAL;
//    }
//}
