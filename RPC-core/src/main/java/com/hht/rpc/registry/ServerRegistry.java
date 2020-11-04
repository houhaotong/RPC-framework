package com.hht.rpc.registry;

/**
 * @author hht
 * @date 2020/10/30 22:29
 */
public interface ServerRegistry {

    /**
     * 注册服务进服务map
     * @param server 服务实体
     * @param <T> 服务类型
     */
    public <T> void register(T server);

    /**
     * 根据服务名获取服务
     * @param serverName 服务名
     * @return 服务实体
     */
    public Object getService(String serverName);
}
