package com.xingxing.oa.redisson;

import org.redisson.api.RLock;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public interface DistributedLocker {
    /**
     *
     * @param obj 加锁对象
     * @param id 加锁对象id
     * @return 锁 key
     */
    static String generatorLockKey(Object obj,Long id){
        return generatorLockKey(obj,id.toString());
    }

    /**
     *
     * @param clazz 加锁类
     * @param id 加锁对象id
     * @return 锁 key
     */
    static String generatorLockKey(Class clazz,Long id){
        return generatorLockKey(clazz,id.toString());
    }

    /**
     *
     * @param obj 加锁对象
     * @param fieldValue 加锁对象某字段值
     * @return 锁 key
     */
    static String generatorLockKey(Object obj,String fieldValue){
        Objects.requireNonNull(fieldValue);
        return String.format("%s:%s",obj.getClass().getName(),fieldValue);
    }

    /**
     *
     * @param clazz 加锁类
     * @param fieldValue 加锁对象某字段值
     * @return 锁 key
     */
    static String generatorLockKey(Class clazz,String fieldValue){
        Objects.requireNonNull(fieldValue);
        return String.format("%s:%s",clazz.getClass().getName(),fieldValue);
    }

    /**
     * 不推荐使用此方法
     * @param obj 加锁对象
     * @param fieldValue 加锁对象某字段值 必须可调用toString，生成可标志此对象的唯一值
     * @return 锁 key
     */
    @Deprecated
    static String generatorLockKey(Object obj,Object fieldValue){
        return generatorLockKey(obj,fieldValue.toString());
    }

    /**
     * 不推荐使用此方法
     * @param clazz 加锁类
     * @param fieldValue 加锁对象某字段值  必须可调用toString，生成可标志此对象的唯一值
     * @return 锁 key
     */
    @Deprecated
    static String generatorLockKey(Class clazz,Object fieldValue){
        return generatorLockKey(clazz,fieldValue.toString());
    }

    RLock lock(String lockKey);
    RLock lock(String lockKey,int timeout);
    RLock lock(String lockKey, TimeUnit unit, int timeout);
    boolean tryLock(String lockKey,TimeUnit unit,int waitTime,int leaseTime);
    boolean tryLock(String lockKey);
    void unlock(String lockKey);
    void unlock(RLock lock);
}