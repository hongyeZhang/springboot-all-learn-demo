package com.zhq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties("server")
public class ServerProperties {

    private String url;

    private App app;

    @Data
    public static class App {
        private String name;
        private String threadCount;
        private List<String> users;
    }

}
