package com.hht.test;

import com.hht.rpc.HelloService;
import com.hht.rpc.registry.DefaultServerRegistry;
import com.hht.rpc.registry.ServerRegistry;
import com.hht.rpc.server.RpcServer;
import com.hht.rpc.server.netty.NettyServer;
import com.hht.rpc.server.socket.SocketServer;

/**
 * @author hht
 * @date 2020/10/28 22:14
 */
public class testServer {

    public static void main(String[] args) {
        RpcServer server=new NettyServer();
        HelloService service=new HelloServiceImpl();
        ServerRegistry registry=new DefaultServerRegistry();
        registry.register(service);
        server.start(registry,8888);
    }
}
