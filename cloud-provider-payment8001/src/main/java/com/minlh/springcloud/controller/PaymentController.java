package com.minlh.springcloud.controller;

import com.minlh.springcloud.entities.CommonResult;
import com.minlh.springcloud.entities.Payment;
import com.minlh.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * 插入数据
     * @param payment
     * @return
     */
    @PostMapping(value="/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        if(result>0) {
            return new CommonResult(200,"数据插入成功！",result);
        }else {
            return new CommonResult(444,"数据插入失败！",null);
        }
    }

    /**
     * 查询数据
     * @param id
     * @return
     */
    @GetMapping(value="/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if(payment!=null) {
            return new CommonResult(200,"获取数据成功001！",payment);
        }else {
            return new CommonResult(444,"获取数据失败！",null);
        }
    }
}
