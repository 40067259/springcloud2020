package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Fred Zhang
 * @create 2020-08-03 11:36 AM
 */
@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("/payment/consul")
    public String paymentConsul(){
        log.info("Consul started,some data will return to users");
        return "Springcloud with consul: "+serverPort+"\t "+ UUID.randomUUID().toString();
    }
}
