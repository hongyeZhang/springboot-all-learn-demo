package com.zhq.spring.kafka.demo.controller;

import com.zhq.spring.kafka.demo.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : ZHQ
 * @date : 2020/1/16
 */
@RestController
public class KafkaTestController {

    @Autowired
    private KafkaProducer producer;

    @RequestMapping("/testSendMsg")
    public String testSendMsg(){
        producer.send();
        return "success";
    }
}
