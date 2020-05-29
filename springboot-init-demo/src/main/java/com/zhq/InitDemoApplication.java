package com.zhq;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class InitDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(InitDemoApplication.class, args);

        ApplicationArguments applicationArguments = context.getBean(ApplicationArguments.class);
        System.out.println("name=" + applicationArguments.getOptionNames());
        System.out.println("values====" + applicationArguments.getOptionValues("developer.name"));
    }
}
