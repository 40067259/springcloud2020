package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author Fred Zhang
 * @create 2020-08-03 12:02 PM
 */
@RestController
@Slf4j
public class OrderConsulController {
    @Resource
    private RestTemplate restTemplate;

    private static final String INVOKE_URL = "http://consul-provider-payment";

    @GetMapping(value = "/consumer/payment/consul")
    public String getInfo(){
        log.info("Consul Consumer side has started and return info to users");
        String result = restTemplate.getForObject(INVOKE_URL + "/payment/consul", String.class);
        return result;
    }
}
