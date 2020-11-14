package com.hht.rpc.registry.loadbalance;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * 负载均衡统一接口
 * @author hht
 * @date 2020/11/9 15:14
 */
public interface LoadBalancer {

    public Instance select(List<Instance> serviceList);
}
