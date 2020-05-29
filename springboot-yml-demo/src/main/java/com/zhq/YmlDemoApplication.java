package com.zhq;

import com.zhq.config.ServerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class YmlDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(YmlDemoApplication.class, args);

        ServerProperties serverProperties = context.getBean(ServerProperties.class);
        System.out.println(serverProperties);



    }
}
