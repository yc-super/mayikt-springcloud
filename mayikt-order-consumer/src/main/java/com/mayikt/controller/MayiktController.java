package com.mayikt.controller;

import com.mayikt.loadbalance.LoadBalance;
import com.mayikt.loadbalance.RandomLoadBalance;
import com.mayikt.loadbalance.RoundLoadBalance;
import com.mayikt.loadbalance.WeightLoadBalance;
import com.mayikt.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class MayiktController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RandomLoadBalance randomLoadBalance;
    @Autowired
    private RoundLoadBalance roundLoadBalance;
    @Autowired
    private WeightLoadBalance weightLoadBalance;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/orderToMember")
    public String orderToMember() {
//        String url = "http://localhost:8081/getMember";
//        return HttpClientUtils.doGet(url, null);

        /*String memberServiceName = "mayikt-member";
        List<ServiceInstance> instances = discoveryClient.getInstances(memberServiceName);
        ServiceInstance serviceInstance = instances.get(0);
        //拼接接口地址
        String memberServerAddress = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/";
        return "调用会员服务返回结果：" + HttpClientUtils.doGet(memberServerAddress + "getMember", null);*/


        //使用RandomLoadBalance实现负载均衡，随机机制
       /* String memberServiceName = "mayikt-member";
        ServiceInstance serviceInstance = randomLoadBalance.getIntance(memberServiceName);
        //拼接接口地址
        String memberServerAddress = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/";
        return "调用会员服务返回结果：" + HttpClientUtils.doGet(memberServerAddress + "getMember", null);*/

        //使用RoundLoadBalance实现负载均衡，轮询机制
        /*String memberServiceName = "mayikt-member";
        ServiceInstance serviceInstance = roundLoadBalance.getIntance(memberServiceName);
        //拼接接口地址
        String memberServerAddress = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/";
        //使用restTemplate而非HttpClient试下rpc远程调用，实际上，restTemplate底层也是用的HttpClient
        return "调用会员服务返回结果：" + restTemplate.getForObject(memberServerAddress + "getMember",String.class);*/

        //模拟集群中某一服务器宕机，如何故障转移
       /* String memberServiceName = "mayikt-member";
        List<ServiceInstance> instances = discoveryClient.getInstances(memberServiceName);
        if (instances == null || instances.size() == 0)
            return null;
        for (int i = 0; i < instances.size(); i++) {
            try {
                String memberServerAddress = "http://" + instances.get(i).getHost() + ":" + instances.get(i).getPort() + "/";
                return "调用会员服务返回结果：" + restTemplate.getForObject(memberServerAddress + "getMember", String.class);
            }catch (RestClientException e){
                log.error("[rpc远程调用发生故障，开始故障转移，切换下一个地址调用 e:{}]"+e);
            }
        }
        return "false";*/

        //负载均衡，权重轮询机制
        /*String memberServiceName = "mayikt-member";
        ServiceInstance serviceInstance = weightLoadBalance.getIntance(memberServiceName);
        //拼接接口地址
        String memberServerAddress = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/";
        //使用restTemplate而非HttpClient试下rpc远程调用，实际上，restTemplate底层也是用的HttpClient
        return "调用会员服务返回结果：" + restTemplate.getForObject(memberServerAddress + "getMember",String.class);*/

        //ribbon负载均衡使用
        String memberServiceName = "mayikt-member";
        ServiceInstance serviceInstance = loadBalancerClient.choose(memberServiceName);
        //拼接接口地址
        String memberServerAddress = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/";
        //使用restTemplate而非HttpClient试下rpc远程调用，实际上，restTemplate底层也是用的HttpClient
        return "调用会员服务返回结果：" + restTemplate.getForObject(memberServerAddress + "getMember",String.class);
    }
}
