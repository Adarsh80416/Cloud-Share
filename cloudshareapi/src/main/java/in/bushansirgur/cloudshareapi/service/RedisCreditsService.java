package in.bushansirgur.cloudshareapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisCreditsService {

    private final StringRedisTemplate redisTemplate;
    private static final String KEY_PREFIX = "credits:";

    /**
     * Unconditionally sets the credit count for a user in Redis.
     * Used when a brand-new profile/credits record is created.
     */
    public void initializeCredits(String clerkId, long amount) {
        redisTemplate.opsForValue().set(KEY_PREFIX + clerkId, String.valueOf(amount));
    }

    /**
     * Sets the credit count only if it doesn't already exist in Redis.
     * Safety net for users who existed before Redis was introduced, or if
     * the Redis instance was ever flushed/restarted without persistence.
     */
    public void initializeCreditsIfAbsent(String clerkId, long amount) {
        redisTemplate.opsForValue().setIfAbsent(KEY_PREFIX + clerkId, String.valueOf(amount));
    }

    public long getCredits(String clerkId) {
        String value = redisTemplate.opsForValue().get(KEY_PREFIX + clerkId);
        return value != null ? Long.parseLong(value) : 0;
    }

    /**
     * Atomically decrements credits by 1. Returns true if the decrement
     * succeeded (credits were available). Returns false if it was rejected
     * (insufficient credits) — in that case the decrement is rolled back
     * automatically, so nothing is actually deducted.
     */
    public boolean tryConsumeCredit(String clerkId) {
        Long remaining = redisTemplate.opsForValue().decrement(KEY_PREFIX + clerkId);
        if (remaining == null) {
            return false;
        }
        if (remaining < 0) {
            redisTemplate.opsForValue().increment(KEY_PREFIX + clerkId); // roll back
            return false;
        }
        return true;
    }

    public void addCredits(String clerkId, long amount) {
        redisTemplate.opsForValue().increment(KEY_PREFIX + clerkId, amount);
    }
}
