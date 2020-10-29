package com.hht.test;

import com.hht.rpc.HelloObj;
import com.hht.rpc.HelloService;
import com.hht.rpc.client.ClientProxy;
import com.hht.rpc.client.RpcClient;
import domain.RpcRequest;

/**
 * @author hht
 * @date 2020/10/29 14:44
 */
public class testClient {
    public static void main(String[] args) {
        ClientProxy clientProxy = new ClientProxy("localhost", 8888);
        HelloService service=clientProxy.getProxy(HelloService.class);
        String res = service.hello(new HelloObj(666, "远程调用测试！"));
        System.out.println(res);
    }
}
