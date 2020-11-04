package com.hht.rpc.server;

import com.hht.rpc.registry.ServerRegistry;

/**
 * @author hht
 * @date 2020/10/31 22:29
 */
public interface RpcServer {

    public void start(ServerRegistry registry,int port);
}
