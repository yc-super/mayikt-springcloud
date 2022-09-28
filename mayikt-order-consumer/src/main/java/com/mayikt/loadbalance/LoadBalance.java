package com.mayikt.loadbalance;

import org.springframework.cloud.client.ServiceInstance;

public interface LoadBalance {

    ServiceInstance getIntance(String serviceId);
}
