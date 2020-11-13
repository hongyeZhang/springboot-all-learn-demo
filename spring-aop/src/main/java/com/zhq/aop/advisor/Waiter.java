package com.zhq.aop.advisor;


public class Waiter {

    public void serveTo(String name) {
        System.out.println("waiter serving " + name + "...");
    }

    public void greetTo(String name) {
        System.out.println("waiter greet to " + name + "...");
    }
}
