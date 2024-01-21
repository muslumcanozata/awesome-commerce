package com.commerce.stock.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Slf4j
public class LockService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final static String PROJECT_NAME = "STOCK_SERVICE-";
    private final static Long LOCK_TIMEOUT = 15000L;

    public void lockWithKey(Long lockKey) throws InterruptedException {
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

    public boolean acquireLock(Long lockKey, long timeout, TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(PROJECT_NAME.concat(lockKey.toString()), "locked", timeout, unit));
    }

    public void releaseLock(Long lockKey) {
        redisTemplate.delete(PROJECT_NAME.concat(lockKey.toString()));
    }

    public void batchLockWithKey(List<Long> lockKeys) {
        if (lockKeys == null || lockKeys.isEmpty()) {
            throw new IllegalArgumentException("Lock keys cannot be null or empty");
        }
        lockKeys.forEach(lockKey -> {
            if (lockKey == null) {
                throw new IllegalArgumentException("Lock key cannot be null or empty");
            }
            try {
                lockWithKey(lockKey);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void batchReleaseLock(List<Long> lockKeys) {
        if (lockKeys == null || lockKeys.isEmpty()) {
            throw new IllegalArgumentException("Lock keys cannot be null or empty");
        }
        lockKeys.forEach(lockKey -> {
            if (lockKey == null) {
                throw new IllegalArgumentException("Lock key cannot be null or empty");
            }
            releaseLock(lockKey);
        });
    }

}