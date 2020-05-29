package com.zhq.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Order(1)
@Component
public class ServerSuccessReport implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("====   order=1 应用启动=====" + Arrays.asList(args));
    }
}
