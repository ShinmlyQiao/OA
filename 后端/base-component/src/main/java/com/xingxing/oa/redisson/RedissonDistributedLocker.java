package com.xingxing.oa.redisson;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@ConditionalOnBean(value = RedissonClient.class)
@Component
public class RedissonDistributedLocker implements DistributedLocker {
 
    @Autowired
    private RedissonClient redissonClient;
 
    /**
     * @param lockKey
     * @return
     */
    @Override
    public RLock lock(String lockKey) {
        return lock(lockKey,20);
    }
 
    /**
     * 自己设置超时时间
     * @param lockKey 锁的key
     * @param timeout  秒  如果是-1，直到自己解锁，否则不会自动解锁
     * @return
     */
    @Override
    public RLock lock(String lockKey, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, TimeUnit.SECONDS);
        return lock;
    }
 
    @Override
    public RLock lock(String lockKey, TimeUnit unit, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout,unit);
        return lock;
    }
 
    /**
     *
     * @param lockKey  锁key
     * @param unit  锁单位
     * @param waitTime   等到最大时间，强制获取锁
     * @param leaseTime  锁失效时间
     * @return
     */
    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime,leaseTime,unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 默认等待时间2s，加锁时间20s
     * @param lockKey
     * @return
     */
    @Override
    public boolean tryLock(String lockKey) {
        return tryLock(lockKey, TimeUnit.SECONDS, 2, 20);
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }
 
    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }
 
}