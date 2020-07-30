package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author Fred Zhang
 * @create 2020-07-25 11:56 PM
 */
@RestController
@Slf4j
public class OrderController {

    //当单体服务的时候因为只有一个服务，我们可以写服务地址：http://localhost:8001
    //public static final String PAYMENT_URL="http://localhost:8001";

    //当时集群服务时，我们要写服务集群的名字（在yaml中配置的名字 spring:application:name: ）
    public static final String PAYMENT_URL="http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create") //Because it comes from front-end side, always GetMapping
    public CommonResult<Payment> create(Payment payment){
        //浏览器只能发get请求，但是底层的实质是调用服务端的Post
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){

        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }
}
