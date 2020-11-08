package com.hht.test;

import com.hht.rpc.HelloService;
import com.hht.rpc.registry.NacosServerRegistry;
import com.hht.rpc.server.RpcServer;
import com.hht.rpc.server.netty.NettyServer;

/**
 * @author hht
 * @date 2020/10/28 22:14
 */
public class testServer {

    public static void main(String[] args) {
        RpcServer server=new NettyServer("127.0.0.1",8888,new NacosServerRegistry());
        HelloService service=new HelloServiceImpl();
        server.publishService(service,HelloService.class);
        server.start();
    }
}
