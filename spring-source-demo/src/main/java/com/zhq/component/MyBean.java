package com.zhq.component;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class MyBean implements FactoryBean {
    private String message;

    public MyBean() {
        this.message = "通过构造方法初始化实例";
    }

    public MyBean(String message) {
        this.message = message;
    }

    @Override
    public Object getObject() throws Exception {
        // 这里并不一定要返回MyBean自身的实例，可以是其他任何对象的实例。
        //如return new Student()...
        return new MyBean("通过FactoryBean.getObject()创建实例");
    }

    @Override
    public Class<?> getObjectType() {
        return MyBean.class;
    }

    public String getMessage() {
        return message;
    }
}
