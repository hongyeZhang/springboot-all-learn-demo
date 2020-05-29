package com.zhq.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ServerProperties.class,OrgProperties.class})
public class ApplicationConfig {

}
