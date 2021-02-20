package com.minlh.springcloud.controller;

import com.minlh.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderHystrixController {

    @Autowired
    private PaymentHystrixService paymentHystrixService;

    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }

    @GetMapping(value = "/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1500")
    })
    public String payment_timeout(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.payment_timeout(id);
        return result;
    }

    /**
     * 服务降级的兜底处理方法
     * @param id
     * @return
     */
    public String paymentInfo_TimeoutHandler(Integer id) {
        return "我是80消费端，服务请求超时，请稍后再试。 paymentInfo_TimeoutHandler, id:"+id+"\t"+"超时测试！";
    }
}
