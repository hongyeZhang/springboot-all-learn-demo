package com.zhq.spring.boot.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : ZHQ
 * @date :
 */
@RestController
public class AspectTestController {

    private static final Logger logger = LoggerFactory.getLogger(AspectTestController.class);
    private static final String CLASS_NAME = Thread.currentThread().getStackTrace()[1].getClassName();

    @RequestMapping(value = "/activity/group/audit/create", method = RequestMethod.POST)
    public String activityGroupAuditCreate(String name) {
        System.out.println("activityGroupAuditCreate");
        return "success";
    }

    @RequestMapping(value = "/activity/group/audit/edit", method = RequestMethod.POST)
    public String activityGroupAuditEdit() {
        System.out.println("activityGroupAuditEdit");
        return "success";
    }


}
