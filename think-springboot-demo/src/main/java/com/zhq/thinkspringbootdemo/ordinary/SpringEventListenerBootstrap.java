package com.zhq.thinkspringbootdemo.ordinary;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @program: think-springboot-demo
 * @description:
 * @author: ZHQ
 * @create: 2019-07-27 23:18
 **/
public class SpringEventListenerBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Object.class)
                .listeners(event -> {
                    System.out.println("springApplication 事件监听器 ：" + event.getClass().getSimpleName());
                })
                .web(WebApplicationType.NONE)
                .run(args)
                .close();

    }
}
