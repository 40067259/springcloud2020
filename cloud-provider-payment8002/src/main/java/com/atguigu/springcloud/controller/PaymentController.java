package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Fred Zhang
 * @create 2020-07-25 1:58 PM
 */
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        System.out.println("ID: "+result);
        log.info("Insert a payment with id: "+ result);
        if(result > 0){
            return new CommonResult(200,"Inserted into database successfully," +
                    "server port:"+serverPort,result);

        }
        else{
            return new CommonResult(444,"Failed to insert a payment");
        }
    }
    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){ //pay attention
        System.out.println("id is "+id);
        Payment payment = paymentService.getPaymentById(id);
        log.info("Query is:  "+payment);
        log.info("The query result is: "+payment);

        if(payment != null){
            return new CommonResult(200,"query successfully, server port: "
                    +serverPort,payment);
        } else{
            return new CommonResult(444,"Failed to query",null);
        }
    }
    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }
}
