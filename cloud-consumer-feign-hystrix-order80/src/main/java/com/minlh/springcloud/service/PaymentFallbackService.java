package com.minlh.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements  PaymentHystrixService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "该方法就是对应接口的兜底方法";
    }

    @Override
    public String payment_timeout(Integer id) {
        return "该方法就是对应接口的兜底方法";
    }
}
