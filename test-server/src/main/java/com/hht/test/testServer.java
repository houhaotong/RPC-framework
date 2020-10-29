package com.hht.test;

import com.hht.rpc.HelloService;
import com.hht.rpc.server.RpcServer;

/**
 * @author hht
 * @date 2020/10/28 22:14
 */
public class testServer {

    public static void main(String[] args) {
        RpcServer server=new RpcServer();
        HelloService service=new HelloServiceImpl();
        server.register(service,8888);
    }
}
