package com.xingxing.oa.redisson;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * redisson的属性文件
 */

@Configuration
@ConfigurationProperties(prefix = "redisson")
@Data
public class RedissonProperties {
    private String address;
    private String password;
    //毫秒
    private int timeout=3000;
    private int database=0;
    private String sentinelAddress;
    private int connectionPoolSize=150;
    private int connectionMiniumIdleSize=10;
    private int slaveConnectionPoolSize=300;
    private int masterConnectionPoolSize=300;
    private String[] sentinelAddresses;
    private String[] masterAddresses;
    //毫秒
    private int scanInterval=2000;
    private String masterName;
}
