package com.xingxing.oa.redis.utils;

import com.xingxing.oa.collection.CollectionUtils;
import com.xingxing.oa.constrants.RuleConstrants;
import com.xingxing.oa.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class RedisUtils {

    @Autowired
    @Lazy
    private RedisTemplate redisTemplate;

    /**
     * 获取生成的domain对象的id值
     * @param generatorIdValue @see GeneratorId value
     * @return
     */
    public Long getDomainId(String generatorIdValue){
        HashOperations hashOperations = redisTemplate.opsForHash();
        Boolean hasKey = hashOperations.hasKey(RuleConstrants.DOMAIN_KEY, generatorIdValue);
        if(hasKey){
            Long idValue = hashOperations.increment(RuleConstrants.DOMAIN_KEY, generatorIdValue, 1L);
            return  idValue;
        }
        throw new RuntimeException("not has this key: "+generatorIdValue);
    }

    public void setStringValue(String key, Object value){
        StringUtils.isBlankMessage(key);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value);
    }

    public Boolean deleteKey(String key){
        StringUtils.isBlankMessage(key);
        return redisTemplate.delete(key);
    }

    public Long deleteKeys(Collection<String> keys){
        CollectionUtils.isEmptyMessage(keys);
        return redisTemplate.delete(keys);
    }

    public Object getStringValue(String key){
        StringUtils.isBlankMessage(key);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void setHashValue(String key,String field,Object value){
        StringUtils.isBlankMessage(key);
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key,field,value);
    }
    public Object getHashValue(String key,String field){
        StringUtils.isBlankMessage(key);
        HashOperations hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key,field);
    }
    public Long delHashKeyField(String key,String... fields){
        StringUtils.isBlankMessage(key);
        HashOperations hashOperations = redisTemplate.opsForHash();
        return hashOperations.delete(key,fields);
    }
}
