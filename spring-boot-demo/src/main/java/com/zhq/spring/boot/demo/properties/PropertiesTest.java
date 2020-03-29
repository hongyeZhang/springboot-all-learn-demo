package com.zhq.spring.boot.demo.properties;

import java.util.Properties;
import java.util.Set;

/**
 * @author : ZHQ
 * @date : 2019/11/24
 */
public class PropertiesTest {

    public static void main(String[] args) {
        //创建Properties集合对象
        Properties prop = new Properties();
        //使用setProperty往集合中添加数据，都是字符串
        prop.setProperty("张三","16");
        prop.setProperty("李四","17");
        prop.setProperty("王五","18");

        //使用stringPropertyNames把Properties集合中的键取出,存储到一个Set集合中
        Set<String> set = prop.stringPropertyNames();

        //遍历Set集合,取出Properties集合的每一个键
        for (String key : set) {
            //使用getProperty方法通过key获取value
            String value = prop.getProperty(key);
            System.out.println(key+"="+value);
        }



    }
}
