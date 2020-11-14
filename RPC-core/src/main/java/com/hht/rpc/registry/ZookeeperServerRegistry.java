package com.hht.rpc.registry;

import com.hht.rpc.provider.ServiceProvider;
import enums.RegistryURL;
import exception.RpcException;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * zookeeper注册中心，包括服务注册和服务发现功能
 *
 * @author hht
 * @date 2020/11/6 17:25
 */
@Slf4j
public class ZookeeperServerRegistry implements ServerRegistry {

    private final ZkClient zkClient;

    public ZookeeperServerRegistry() {
        zkClient = new ZkClient(RegistryURL.ZKADDRESS.getUrl(), 4000, 1000);
    }

    @Override
    public void register(String serviceName, InetSocketAddress serviceAddress) {
        String registryPath = "/registry";
        //创建 registry永久节点
        if (!zkClient.exists(registryPath)) {
            zkClient.createPersistent(registryPath);
        }
        //创建 service永久节点
        String servicePath = registryPath + "/" + serviceName;
        if (!zkClient.exists(servicePath)) {
            zkClient.createPersistent(servicePath);
        }
        //创建 存储服务提供者地址的 顺序临时节点
        String addressPath = servicePath + "/address";
        zkClient.createEphemeralSequential(addressPath, serviceAddress);
        log.info("----创建临时节点:{}",addressPath);
        log.info("{}服务注册成功！",serviceName);
    }
}
