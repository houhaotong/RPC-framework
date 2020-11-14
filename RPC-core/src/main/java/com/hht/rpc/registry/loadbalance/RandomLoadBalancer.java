package com.hht.rpc.registry.loadbalance;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机策略
 * @author hht
 * @date 2020/11/9 15:16
 */
public class RandomLoadBalancer implements LoadBalancer {

    private final ThreadLocalRandom random=ThreadLocalRandom.current();
    @Override
    public Instance select(List<Instance> serviceList) {
        int size=serviceList.size();
        int index = random.nextInt(size);
        return serviceList.get(index);
    }
}
