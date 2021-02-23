package com.minlh.springcloud.controller;

import com.minlh.springcloud.entities.CommonResult;
import com.minlh.springcloud.entities.Payment;
import com.minlh.springcloud.service.PaymentService;
import com.netflix.appinfo.InstanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 插入数据
     * @param payment
     * @return
     */
    @PostMapping(value="/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        if(result>0) {
            return new CommonResult(200,"数据插入成功！,serverport="+serverPort,result);
        }else {
            return new CommonResult(444,"数据插入失败！,serverport="+serverPort,null);
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
            return new CommonResult(200,"获取数据成功001！,serverport="+serverPort,payment);
        }else {
            return new CommonResult(444,"获取数据失败！,serverport="+serverPort,null);
        }
    }

    /**
     * 提供暴露自己服务的入口
     * @return
     */
    @GetMapping(value="/payment/discovery")
    public Object discoveryInfo() {
        //获取Eureka服务器上注册是的所有服务
        List<String> services = discoveryClient.getServices();
        for(String serverName : services) {
            System.out.println("Eureka服务名称是："+serverName);
            System.out.println("该服务"+serverName+"名下有的实例为：");
            List<ServiceInstance> instances = discoveryClient.getInstances(serverName);
            for(ServiceInstance instance :instances) {
                System.out.println("-----serviceId："+instance.getServiceId());
                System.out.println("-----实例名称："+instance.getHost());
                System.out.println("-----实例端口："+instance.getPort());
                System.out.println("-----实例URL："+instance.getUri());
            }
        }
        return discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }
}
