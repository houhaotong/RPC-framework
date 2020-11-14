package com.hht.rpc.registry.serverdiscover;

import java.net.InetSocketAddress;

/**
 * 服务发现
 * @author hht
 * @date 2020/11/10 16:58
 */
public interface ServerDiscovery {

    /**
     * 服务发现
     * @param serviceName 服务名称
     * @return 服务地址
     */
    public InetSocketAddress discover(String serviceName);
}
