package com.minlh.springcloud.service.impl;

import com.minlh.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "线程池： "+Thread.currentThread().getName() +" paymentInfo_ok, id:"+id+"\t"+"正常的测试！";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "线程池： "+Thread.currentThread().getName() +" paymentInfo_timeout, id:"+id+"\t"+"超时测试！";
    }
}
