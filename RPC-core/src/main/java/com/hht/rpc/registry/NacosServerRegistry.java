package com.hht.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.hht.rpc.registry.loadbalance.LoadBalancer;
import com.hht.rpc.registry.loadbalance.RandomLoadBalancer;
import com.hht.rpc.utils.NacosUtil;
import exception.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
/**
 * 使用nacos做为注册中心
 * @author hht
 * @date 2020/11/8 20:08
 */
@Slf4j
public class NacosServerRegistry implements ServerRegistry{

    private static final String SERVER_ADDR="127.0.0.1:8848";

    @Override
    public void register(String serviceName, InetSocketAddress serviceAddress) {
        try {
            //注册服务实例
            NacosUtil.register(serviceName,serviceAddress);
        } catch (NacosException e) {
            throw new RpcException("Nacos服务注册失败");
        }
    }
}
