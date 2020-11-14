package com.hht.test;

import com.hht.rpc.HelloService;
import com.hht.rpc.registry.NacosServerRegistry;
import com.hht.rpc.server.netty.NettyServer;

/**
 * @author hht
 * @date 2020/11/9 21:00
 */
public class testServer2 {

    public static void main(String[] args) {
        NettyServer server=new NettyServer("127.0.0.1",9999,new NacosServerRegistry());
        server.start();
    }
}
