package com.personal.tmall.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author YunLung Chia
 * @date 2023/6/11
 */
@Data
@Component
@ConfigurationProperties(prefix="redis")
public class RedisConfig {
    private String host;
    private int port;
    /**
     * 秒
     */
    private int timeout;
    private String password;
    private int poolMaxTotal;
    private int partition;
    private int poolMaxIdle;
    private int poolMaxWait;
}
