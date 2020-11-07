package com.hht.rpc.provider;

import enums.RpcError;
import exception.RpcException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hht
 * @date 2020/10/30 22:43
 */
public class ServiceProviderImpl implements ServiceProvider {

    /** 存储服务,key为这个服务所实现的接口，设置为静态变量，在全局范围内使用 */
    private static final Map<String, Object> SERVICE_MAP =new ConcurrentHashMap<>();
    /** 用于存放注册过的服务 */
    private static final Set<String> REGISTERED_SERVER =new HashSet<>();

    @Override
    public <T> void addService(T server) {
        String serverName = server.getClass().getCanonicalName();
        if(REGISTERED_SERVER .add(serverName)){
            Class<?>[] interfaces = server.getClass().getInterfaces();
            if(interfaces.length==0){
                throw new RpcException(RpcError.NOT_IMPLEMENTS_INTERFACES);
            }
            for(Class<?> i:interfaces){
                SERVICE_MAP.put(i.getCanonicalName(),server);
            }
        }
    }

    @Override
    public Object getService(String serverName) {
        Object o = SERVICE_MAP.get(serverName);
        if (o==null){
            throw new RpcException(RpcError.NOT_FOUND);
        }
        return o;
    }
}
