package com.hht.rpc.registry;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * 注册中心
 * @author hht
 * @date 2020/11/6 23:23
 */
public interface ServerRegistry {

    /**
     * 注册服务
     * @param serviceName 服务名称
     * @param serviceAddress 服务提供地址
     */
    public void register(String serviceName, InetSocketAddress serviceAddress);
}

