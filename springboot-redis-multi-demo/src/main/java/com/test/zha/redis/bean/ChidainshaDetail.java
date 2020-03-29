package com.test.zha.redis.bean;

import java.io.Serializable;

/**
 * @program: redisTestNew
 * @description:
 * @author: ZHQ
 * @create: 2019-06-07 22:58
 **/
public class ChidainshaDetail implements Serializable {
    private String source;
    private String businessId;

    public ChidainshaDetail() {
    }

    public ChidainshaDetail(String source, String businessId) {
        this.source = source;
        this.businessId = businessId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    @Override
    public String toString() {
        return "ChidainshaDetail{" +
                "source='" + source + '\'' +
                ", businessId='" + businessId + '\'' +
                '}';
    }
}
