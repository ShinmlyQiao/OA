package com.xingxing.oa.redisson;

import com.xingxing.oa.string.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson自动配置类
 */

@Configuration
@ConditionalOnBean(value = RedissonProperties.class)
public class RedissonAutoConfiguration {
 
    @Autowired
    private RedissonProperties redissonProperties;
 
 
    /**
     * 单体的
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "redisson.address")
    public RedissonClient redissonSingle(){
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer().setAddress(redissonProperties.getAddress())
                .setTimeout(redissonProperties.getTimeout())
                .setDatabase(redissonProperties.getDatabase())
                .setConnectionPoolSize(redissonProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redissonProperties.getConnectionMiniumIdleSize());
        if(StringUtils.isNotBlank(redissonProperties.getPassword())){
            serverConfig.setPassword(redissonProperties.getPassword());
        }
        return Redisson.create(config);
    }
}