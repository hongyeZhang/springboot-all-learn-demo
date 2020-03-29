package com.zhq.thinkspringbootdemo;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThinkSpringbootDemoApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ThinkSpringbootDemoApplication.class, args);

        SpringApplication app = new SpringApplication(ThinkSpringbootDemoApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
