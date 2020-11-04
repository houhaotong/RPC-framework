package com.hht.rpc.client;

import enums.RpcError;
import domain.RpcRequest;
import domain.RpcResponse;
import exception.RpcException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理的方式，将远程调用返回的结果当做使用对应方法的返回值
 * @author hht
 * @date 2020/10/28 15:35
 */
public class ClientProxy implements InvocationHandler {

    private String host;
    private Integer port;
    private RpcClient client;

    public ClientProxy(String host,Integer port,RpcClient client){
        this.host=host;
        this.port=port;
        this.client=client;
    }
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest=new RpcRequest.Builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .parameterTypes(method.getParameterTypes())
                .build();
        RpcResponse response = (RpcResponse) client.sendRequest(rpcRequest, host, port);
        if(response==null){
            throw new RpcException(RpcError.BAD_SERVER);
        }
        return response.getData();
    }
}
