package com.minlh.springcloud.service.impl;

import com.minlh.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

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
}
