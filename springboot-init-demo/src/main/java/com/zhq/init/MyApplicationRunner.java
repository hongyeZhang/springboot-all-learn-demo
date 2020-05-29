package com.zhq.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("===MyApplicationRunner===" + Arrays.asList(args.getSourceArgs()));
        System.out.println("===getOptionNames========" + args.getOptionNames());
        // --foo=bar --developer.name=zhq
        System.out.println("===getOptionValues=======" + args.getOptionValues("foo"));
        System.out.println("==getOptionValues========" + args.getOptionValues("developer.name"));
    }
}
