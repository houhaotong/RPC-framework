package com.hht.test;

import com.hht.rpc.HelloObj;
import com.hht.rpc.HelloService;
import com.hht.rpc.client.ClientProxy;
import com.hht.rpc.client.netty.NettyClient;
import domain.RpcRequest;

/**
 * @author hht
 * @date 2020/10/29 14:44
 */
public class testClient {
    public static void main(String[] args) {
        NettyClient client = new NettyClient();
        ClientProxy clientProxy = new ClientProxy("localhost", 8888, client);
        HelloService helloService=clientProxy.getProxy(HelloService.class);
        String msg=helloService.hello(new HelloObj(666,"哈哈哈哈哈哈哈哈哈哈哈哈"));
        System.out.println(msg);
    }
}
