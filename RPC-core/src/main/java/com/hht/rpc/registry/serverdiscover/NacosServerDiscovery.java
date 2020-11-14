package com.hht.rpc.registry.serverdiscover;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.hht.rpc.registry.loadbalance.LoadBalancer;
import com.hht.rpc.registry.loadbalance.LoadBalancerFactory;
import com.hht.rpc.utils.NacosUtil;
import enums.LoadBalanceType;
import exception.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Nacos服务发现
 *
 * @author hht
 * @date 2020/11/10 16:59
 */
@Slf4j
public class NacosServerDiscovery implements ServerDiscovery {

    private final LoadBalancer lb;

    public NacosServerDiscovery(LoadBalanceType loadBalanceType) {
        this.lb = LoadBalancerFactory.createLoadBalancer(loadBalanceType);
    }
    public NacosServerDiscovery(){
        //设置默认负载均衡策略为随机策略
        this.lb=LoadBalancerFactory.createLoadBalancer(LoadBalanceType.RANDOM);
    }

    @Override
    public InetSocketAddress discover(String serviceName) {
        //获取一个服务的所有提供者实例
        List<Instance> instances = NacosUtil.getAllInstances(serviceName);
        if (instances.size() == 0) {
            log.error("未发现服务-{}", serviceName);
            throw new RpcException("未发现对应的服务");
        } else {
            //调用对应的负载均衡策略
            Instance instance = lb.select(instances);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        }

    }
}
