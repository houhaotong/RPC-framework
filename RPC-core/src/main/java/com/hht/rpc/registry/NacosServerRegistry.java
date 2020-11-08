package com.hht.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import exception.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 使用nacos做为注册中心
 * @author hht
 * @date 2020/11/8 20:08
 */
@Slf4j
public class NacosServerRegistry implements ServerRegistry{

    private final NamingService namingService;
    private static final String SERVER_ADDR="127.0.0.1:8848";

    public NacosServerRegistry() {
        try {
            namingService=NamingFactory.createNamingService(SERVER_ADDR);

        } catch (NacosException e) {
            log.error("{}-初始化异常!",this.getClass().getCanonicalName());
            throw new RpcException("Nacos连接失败！");
        }
    }

    @Override
    public void register(String serviceName, InetSocketAddress serviceAddress) {
        try {
            //注册服务实例
            namingService.registerInstance(serviceName,serviceAddress.getHostName(),serviceAddress.getPort());
        } catch (NacosException e) {
            throw new RpcException("Nacos服务注册失败");
        }
    }

    @Override
    public InetSocketAddress discover(String serviceName) {
        try {
            //获取一个服务的所有提供者实例
            List<Instance> instances = namingService.getAllInstances(serviceName);
            if(instances.size()==0){
                log.error("未发现服务-{}",serviceName);
                throw new RpcException("未发现对应的服务");
            }else if(instances.size()==1){
                //只有一个提供者实例，则返回第一个实例
                Instance instance=instances.get(0);
                return new InetSocketAddress(instance.getIp(),instance.getPort());
            }else{
                //有多个提供者实例，则随机返回一个实例
                Instance instance=instances.get(ThreadLocalRandom.current().nextInt(instances.size()));
                return new InetSocketAddress(instance.getIp(),instance.getPort());
            }
        } catch (NacosException e) {
            throw new RpcException("Nacos服务发现失败");
        }
    }
}
