package com.zhq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("formatter")
public class DateFormatProperties {

    /**
     * default format pattern
     */
    private String pattern = "yyyy-MM-dd HH:mm:ss";

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
