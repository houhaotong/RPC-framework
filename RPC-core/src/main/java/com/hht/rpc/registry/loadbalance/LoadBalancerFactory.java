package com.hht.rpc.registry.loadbalance;

import enums.LoadBalanceType;
import exception.RpcException;

/**
 * 负载均衡工厂
 * @author hht
 * @date 2020/11/10 21:21
 */
public class LoadBalancerFactory {

    /**
     * 选择负载均衡策略
     * @param type 类型
     * @return 创建好的实例
     */
    public static LoadBalancer createLoadBalancer(LoadBalanceType type){
        if(type==LoadBalanceType.RANDOM){
            return new RandomLoadBalancer();
        }else if(type==LoadBalanceType.ROUNDROBIN){
            return new RoundRobinLoadBalancer();
        }else if(type==LoadBalanceType.WEIGHTRANDOM){
            return new WeightRandomLoadBalancer();
        }else{
            throw new RpcException("未找到对应的LoadBalancer");
        }
    }
}
