package com.zhq.spring.boot.demo.tmp;

import java.lang.reflect.Field;

/**
 * @author : ZHQ
 * @date : 2019/11/24
 */
public class TmpTest {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.zhq.spring.boot.demo.tmp.User");
        Field[] fields = aClass.getFields();
        System.out.println(fields.length);
        for (Field field : fields) {
            System.out.println(field.toString());
        }


    }
}
