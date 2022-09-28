package com.mayikt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
public class AppOrder {
    public static void main(String[] args) {
        SpringApplication.run(AppOrder.class);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
