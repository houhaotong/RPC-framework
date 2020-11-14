package com.hht.rpc.server;

import com.hht.rpc.provider.ServiceProvider;

import java.net.InetSocketAddress;

/**
 * @author hht
 * @date 2020/10/31 22:29
 */
public interface RpcServer {

    public void start();

    public void publishService(Object service,String serviceName);
}
