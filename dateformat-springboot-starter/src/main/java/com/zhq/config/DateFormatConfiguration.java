package com.zhq.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
@EnableConfigurationProperties(DateFormatProperties.class)
@ConditionalOnProperty(prefix = "formatter", name = "enabled", havingValue = "true")
public class DateFormatConfiguration {

    private DateFormatProperties dateFormatProperties;

    public DateFormatConfiguration(DateFormatProperties dateFormatProperties) {
        this.dateFormatProperties = dateFormatProperties;
    }

    @Bean(name = "dateFormatter")
    public SimpleDateFormat dateFormatter() {
        System.out.println("start to initialize SimpleDateFormat with pattern: " + dateFormatProperties.getPattern());
        return new SimpleDateFormat(dateFormatProperties.getPattern());
    }
}
