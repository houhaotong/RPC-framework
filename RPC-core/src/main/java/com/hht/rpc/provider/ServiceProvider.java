package com.hht.rpc.provider;

/**
 * @author hht
 * @date 2020/10/30 22:29
 */
public interface ServiceProvider {

    /**
     * 注册服务进服务map
     * @param service 服务实体
     * @param <T> 服务类型
     */
    public <T> void addService(T service);

    /**
     * 服务发现,根据服务名获取服务
     * @param serverName 服务名
     * @return 服务实体
     */
    public Object getService(String serverName);
}
