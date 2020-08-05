package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import com.sun.jndi.toolkit.url.Uri;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

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
    @Resource
    private LoadBalancer loadBalancer;// The LB we defined by ourselves
    @Resource
    private DiscoveryClient discoveryClient;//拿到注册中心微服务的信息（全部的或者具体每一个的）

    @GetMapping("/consumer/payment/create") //Because it comes from front-end side, always GetMapping
    public CommonResult<Payment> create(Payment payment){
        //浏览器只能发get请求，但是底层的实质是调用服务端的Post
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){

        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }
//Use restTemplate.getForEntity 可以得到额外的信息
    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id){

        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        log.info(entity.getStatusCode()+"/t"+entity.getHeaders());//额外信息
        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return new CommonResult<>(444,"Failed to get messages");
        }
    }

    @GetMapping("/consumer/payment/createForEntity") //Because it comes from front-end side, always GetMapping
    public CommonResult<Payment> create2(Payment payment){
        //浏览器只能发get请求，但是底层的实质是调用服务端的Post
        ResponseEntity<CommonResult> entity = restTemplate.postForEntity(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
        log.info(entity.getStatusCode()+"/t"+entity.getBody());
        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return new CommonResult<>(444,"Failed to get messages");
        }
    }
    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentLB(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(instances == null || instances.size() ==0){
            return null;
        }
        ServiceInstance instanceInUse = loadBalancer.instances(instances);
        URI uri = instanceInUse.getUri();
        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }
}
