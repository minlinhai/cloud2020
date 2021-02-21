package com.minlh.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.minlh.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private Long runNum = 0L;

    @Override
    public String paymentInfo_OK(Integer id) {
        runNum += 1;
        return "第"+runNum+"次被调用，线程池： "+Thread.currentThread().getName() +" paymentInfo_ok, id:"+id+"\t"+"正常的测试！";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="5000")
    })
    @Override
    public String paymentInfo_Timeout(Integer id) {
        runNum += 1;
        int timeout = 3000;
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "第"+runNum+"次被调用，线程池： "+Thread.currentThread().getName() +" paymentInfo_timeout, id:"+id+"\t"+"超时测试！";
    }

    /**
     * 服务降级的兜底处理方法
     * @param id
     * @return
     */
    public String paymentInfo_TimeoutHandler(Integer id) {
        return "服务请求超时，请稍后再试。 paymentInfo_TimeoutHandler, id:"+id+"\t"+"超时测试！";
    }

    /**
     *服务熔断部分
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),// 是否开启断路器
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "10000"),// 时间窗口期
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "60")// 失败率达到多少开启断路器
    })
    public String paymentCircuitBreak(@PathVariable("id") Integer id) {
        if(id<0) {
            throw new RuntimeException("************id值不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功,流水号为："+serialNumber;
    }

    //断路器用到的兜底方法
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id不能为负数，请稍后再试!";
    }
}
