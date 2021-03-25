package com.minlh.cloudalibaba.controller;

import ch.qos.logback.core.util.TimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlowLimitController {
    @GetMapping(value = "/testA")
    public String testA() {
        return "------------testA";
    }

    @GetMapping(value = "/testB")
    public String testB() {
        return "------------testB";
    }

    @GetMapping(value = "/testD")
    public String testD() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test 测试RT");
        return "------------testD";
    }
}
