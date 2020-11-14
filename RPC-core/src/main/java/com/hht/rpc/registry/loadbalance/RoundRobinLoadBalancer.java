package com.hht.rpc.registry.loadbalance;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * 轮询策略,使用单例模式
 * @author hht
 * @date 2020/11/9 20:31
 */
public class RoundRobinLoadBalancer implements LoadBalancer {

    private Integer pos=0;

    @Override
    public Instance select(List<Instance> serviceList) {
        Instance res;
        synchronized (pos){
            if(pos>=serviceList.size()){
                pos=0;
            }
            res=serviceList.get(pos);
            pos++;
        }
        return res;
    }
}
