package com.minlh.springcloud.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

public interface PaymentService {

    public String paymentInfo_OK(Integer id);

    public String paymentInfo_Timeout(Integer id);

    public String paymentCircuitBreak(Integer id);

}
