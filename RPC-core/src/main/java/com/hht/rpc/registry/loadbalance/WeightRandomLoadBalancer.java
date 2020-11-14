package com.hht.rpc.registry.loadbalance;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机加权策略
 * @author hht
 * @date 2020/11/9 16:58
 */
public class WeightRandomLoadBalancer implements LoadBalancer {

    private final ThreadLocalRandom random=ThreadLocalRandom.current();
    @Override
    public Instance select(List<Instance> serviceList) {
        List<Instance> newList = new ArrayList<>();
        TreeMap<Double,Instance> treemap=new TreeMap<>();
        double weight=0;
        for(Instance instance:serviceList){
            weight+= instance.getWeight();
            treemap.put(weight,instance);
        }
        double num=random.nextDouble(weight);
        Double key = treemap.tailMap(num, true).firstKey();
        return treemap.get(key);
    }
}
