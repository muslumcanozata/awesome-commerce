package com.commerce.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Slf4j
public class LockService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final static String PROJECT_NAME = "ORDER_SERVICE-";
    private final static Long LOCK_TIMEOUT = 15000L;

    public void lockWithKey(String lockKey) throws InterruptedException {
        if (acquireLock(lockKey, LOCK_TIMEOUT, TimeUnit.MILLISECONDS)) {
            log.info("Lock acquired for: {}. Operation started.", lockKey);

            Thread.sleep(200);

            log.info("Operation completed.");
        } else {
            log.error("Failed to acquire lock. Resource is busy.");
            Thread.sleep(1000);
            lockWithKey(lockKey);
        }
    }

    public boolean acquireLock(String lockKey, long timeout, TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(PROJECT_NAME.concat(lockKey), "locked", timeout, unit));
    }

    public void releaseLock(String lockKey) {
        redisTemplate.delete(lockKey);
    }
}