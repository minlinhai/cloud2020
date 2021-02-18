package com.minlh.springcloud.service;

import org.springframework.stereotype.Service;

public interface PaymentService {

    public String paymentInfo_OK(Integer id);

    public String paymentInfo_Timeout(Integer id);

}
