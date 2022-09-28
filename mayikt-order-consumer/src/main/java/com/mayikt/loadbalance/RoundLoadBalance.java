package com.mayikt.loadbalance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RoundLoadBalance implements LoadBalance {
    @Autowired
    private DiscoveryClient discoveryClient;
    private AtomicInteger atomicInteger = new AtomicInteger(-1);

    @Override
    public ServiceInstance getIntance(String serviceId) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        if (instances == null || instances.size() == 0)
            return null;
        //轮询算法实现
        int index = atomicInteger.getAndIncrement() % instances.size();
        return instances.get(index);
    }

    public static void main(String[] args) {
        ServiceInstance intance = new RoundLoadBalance().getIntance("mayikt-member");
        System.out.println();
    }
}
