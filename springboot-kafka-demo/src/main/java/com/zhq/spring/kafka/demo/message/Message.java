package com.zhq.spring.kafka.demo.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : ZHQ
 * @date : 2020/1/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private long id;
    private String msg;
    private Date sendTime;

}
