package com.mayikt.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//ribbon没有提供轮询负载均衡方法，需要自己实现
//注意MayiktWeightLoadBalance和LoadBalancerConfig只能存在一个，不然有个类的实现就有两个，会报错
@Component
public class MayiktWeightLoadBalance extends AbstractLoadBalancerRule {

    private AtomicInteger atomicInteger=new AtomicInteger();
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        List<Server> upList = lb.getReachableServers();
        ArrayList<NacosServer> servers = new ArrayList<>();
        upList.forEach((server -> {
            NacosServer nacosServer=(NacosServer)server;
            double weight = nacosServer.getInstance().getWeight();
            for (int i = 0; i < weight; i++) {
                servers.add(nacosServer);
            }
        }));

        return servers.get(atomicInteger.incrementAndGet()%servers.size());

    }
}
