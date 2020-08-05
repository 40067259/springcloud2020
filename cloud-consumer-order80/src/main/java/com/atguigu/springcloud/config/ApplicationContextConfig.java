package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Fred Zhang
 * @create 2020-07-26 12:23 AM
 */
@Configuration
public class ApplicationContextConfig {
    @Bean
    //@LoadBalanced     we don't use Ribbon's LB,use we defined LB
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    /**
     * @Configuration与@Bean结合使用。@Configuration可理解为用spring的时候xml里面的<beans>标签，
     * @Bean可理解为用spring的时候xml里面的<bean>标签。Spring Boot不是spring的加强版，
     * 所以@Configuration和@Bean同样可以用在普通的spring项目中，而不是Spring Boot特有的，
     * 只是在spring用的时候，注意加上扫包配置。
     *
     * applicationContext.xml  <bean id="" class=""></bean>

     */
}
