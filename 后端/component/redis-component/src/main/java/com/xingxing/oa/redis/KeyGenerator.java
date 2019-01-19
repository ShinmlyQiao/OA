package com.xingxing.oa.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


public class KeyGenerator {

    @Autowired
    private RedisTemplate redisTemplate;


}
