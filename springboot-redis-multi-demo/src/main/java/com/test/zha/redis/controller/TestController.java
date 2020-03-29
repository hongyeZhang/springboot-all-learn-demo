package com.test.zha.redis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.zha.redis.bean.ChidainshaDetail;
import com.test.zha.redis.config.DefaultRedisDataSourceInfo;
import com.test.zha.redis.config.SecondRedisDataSourceInfo;
import com.test.zha.redis.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: redisTestNew
 * @description: test
 * @author: ZHQ
 * @create: 2018-11-09 13:40
 **/

@RestController
public class TestController {

    @Autowired
    private DefaultRedisDataSourceInfo defaultRedisInfo;

    @Autowired
    private SecondRedisDataSourceInfo secondRedisDataSourceInfo;

    @RequestMapping(value = "/set")
    public String setTest() {
        return "success";
    }

    @RequestMapping(value = "/get1")
    public String getTest() throws JsonProcessingException {
        List<Object> objectList = (List<Object>) RedisUtils.getValue("21");
        String data = new ObjectMapper().writeValueAsString(objectList);
        data = "{\"rtnCode\":0, \"rtnMsg\":null, \"data\":" + data + "}";
        return data;
    }

    @RequestMapping(value = "/get2")
    public String getTest2() throws JsonProcessingException {
        List<Object> objectList = (List<Object>) RedisUtils.getValue2("21");
        String data = new ObjectMapper().writeValueAsString(objectList);
        data = "{\"rtnCode\":0, \"rtnMsg\":null, \"data\":" + data + "}";
        return data;
    }

    @RequestMapping(value = "/get3")
    public String getTest3() {
        String ret = defaultRedisInfo.getDatabase() + "-" + defaultRedisInfo.getPool().getMaxActive();
        String ret2 = secondRedisDataSourceInfo.getDatabase() + "-" + secondRedisDataSourceInfo.getPool().getMaxActive();
        return ret + " & " + ret2;
    }

    @RequestMapping(value = "/set2")
    public String setTest2() {
        ChidainshaDetail detail = new ChidainshaDetail("123", "hello");
        List<ChidainshaDetail> detailList = new ArrayList<>();

        detailList.add(detail);
        detailList.add( detail);

        String ret = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
             ret = mapper.writeValueAsString(detailList);
        } catch (JsonProcessingException e) {
        }

        RedisUtils.setValue2("22", ret);

        return "success";
    }

    @RequestMapping(value = "/get4")
    public String getTest4() throws IOException {
        String tempStr = (String) RedisUtils.getValue2("22");
        ObjectMapper mapper = new ObjectMapper();

/*        List<ChidainshaDetail> detailList =  mapper.readValue(tempStr,new TypeReference<List<ChidainshaDetail>>() { });
        ChidainshaDetail detail1 = detailList.get(0);*/

        String str = "{\"rtnCode\":0, \"rtnMsg\":null, \"data\":" + tempStr + "}";
        return str;
    }


    @RequestMapping(value = "/set5")
    public String setTest5() {
        List<ChidainshaDetail> detailList = new ArrayList<>();
        String ret = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            ret = mapper.writeValueAsString(detailList);
        } catch (JsonProcessingException e) {
        }

        RedisUtils.setValue2("24", ret);


        return "successs";

    }

    @RequestMapping(value = "/delete5")
    public String deleteTest() {
        RedisUtils.deleteValue2("24");
        RedisUtils.deleteValue2("25");
        return "success";

    }

    @RequestMapping(value = "/set10")
    public String setTest10() {
        RedisUtils.setValue("cms-count", "10");
        return "success";
    }


}
