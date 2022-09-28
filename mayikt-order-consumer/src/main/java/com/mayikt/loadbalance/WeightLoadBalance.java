package com.mayikt.loadbalance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class WeightLoadBalance implements LoadBalance{

    @Autowired
    private DiscoveryClient discoveryClient;
    private AtomicInteger count=new AtomicInteger(-1);

    @Override
    public ServiceInstance getIntance(String serviceId) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        if(instances==null||instances.size()==0)
            return null;
        ArrayList<ServiceInstance> arrayList = new ArrayList<>();
        instances.forEach(serviceInstance -> {
            double weight = Double.valueOf(serviceInstance.getMetadata().get("nacos.weight"));
            for (int i = 0; i < weight; i++) {
                arrayList.add(serviceInstance);
            }
        });
        return arrayList.get(count.incrementAndGet()%arrayList.size());
    }
}
