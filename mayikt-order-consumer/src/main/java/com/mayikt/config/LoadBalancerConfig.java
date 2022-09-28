package com.mayikt.config;

import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//ribbon提供了随机负载均衡，只需要注册成bean就行
@Configuration
public class LoadBalancerConfig {

    /*@Bean
    public RandomRule RandomRule(){
        return new RandomRule();
    }*/
}
