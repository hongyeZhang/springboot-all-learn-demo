package com.zhq.lock.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "redis.config")
public class RedisProperties {
    private int max_idle;

    private int max_wait;

    private String host;

    private int port;

    private int retry_num;

    private int timeout;

}
