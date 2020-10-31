package com.hht.rpc.registry;

import domain.RpcError;
import exception.RpcException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hht
 * @date 2020/10/30 22:43
 */
public class DefaultServerRegistry implements ServerRegistry {

    /** 存储服务,key为这个服务所实现的接口 */
    private final Map<String, Object> serviceMap =new ConcurrentHashMap<>();
    /** 用于存放注册过的服务 */
    private final Set<String> registeredServer=new HashSet<>();

    @Override
    public <T> void register(T server) {
        String serverName = server.getClass().getCanonicalName();
        if(registeredServer.add(serverName)){
            Class<?>[] interfaces = server.getClass().getInterfaces();
            if(interfaces.length==0){
                throw new RpcException(RpcError.NOT_IMPLEMENTS_INTERFACES);
            }
            for(Class<?> i:interfaces){
                serviceMap.put(i.getCanonicalName(),server);
            }
        }
    }

    @Override
    public Object getServer(String serverName) {
        Object o = serviceMap.get(serverName);
        if (o==null){
            throw new RpcException(RpcError.NOT_FOUND);
        }
        return o;
    }
}
