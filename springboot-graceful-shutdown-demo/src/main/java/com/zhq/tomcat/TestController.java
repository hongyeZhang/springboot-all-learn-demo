package com.zhq.tomcat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);


    @GetMapping(value = "/sleep/one", produces = "application/json")
    public String sleepOne(String systemNo) {
        logger.info("模拟业务处理1分钟，请求参数：{}", systemNo);
        Long serverTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < serverTime + (20 * 1000)) {
            logger.info("正在处理业务，当前时间：{}，开始时间：{}", System.currentTimeMillis(), serverTime);
        }
        logger.info("模拟业务处理1分钟，响应参数：{}", "success");
        return "success";
    }


}
