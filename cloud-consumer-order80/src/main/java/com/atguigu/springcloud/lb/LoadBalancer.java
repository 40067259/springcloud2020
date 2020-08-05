package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;


/**
 * @author Fred Zhang
 * @create 2020-08-05 11:57 AM
 */
//面向接口编程
public interface LoadBalancer {


    //从微服务的所有instances中选择一个为我们服务
    ServiceInstance instances(List<ServiceInstance> serviceInstances);



}
