package com.zhq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class StarterTestApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(StarterTestApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(simpleDateFormat.format(new Date()));
    }

    @Autowired
    private SimpleDateFormat simpleDateFormat;
}
