package com.hht.rpc.server;

import com.hht.rpc.anno.Service;
import com.hht.rpc.anno.ServiceScan;
import com.hht.rpc.provider.ServiceProvider;
import com.hht.rpc.registry.ServerRegistry;
import com.hht.rpc.utils.MyClassUtil;
import exception.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.Set;

/**
 * 抽象服务类
 *
 * @author hht
 * @date 2020/11/14 21:28
 */
@Slf4j
public abstract class AbstractRpcServer implements RpcServer {

    protected String host;
    protected int port;

    protected ServerRegistry serverRegistry;
    protected ServiceProvider serviceProvider;

    @Override
    public void publishService(Object service, String serviceName) {
        //添加服务
        serviceProvider.addService(service);
        //注册到注册中心
        serverRegistry.register(serviceName, new InetSocketAddress(host, port));
    }

    /**
     * 扫描服务
     */
    public void scanServices() {
        String mainClassName = MyClassUtil.getMainClass();
        Class<?> mainClass;
        try {
            mainClass = Class.forName(mainClassName);
            //如果没有加注解，则抛出异常
            if (!mainClass.isAnnotationPresent(ServiceScan.class)) {
                log.error("请在启动类加上@serviceScan注解");
                throw new RpcException("无@serviceScan注解");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RpcException("未找到主启动类");
        }
        String scanScope = mainClass.getAnnotation(ServiceScan.class).scope();
        Set<Class<?>> classes = MyClassUtil.getClasses(scanScope);
        for (Class<?> clazz : classes) {
            //如果类型带有service注解，则获取实现的接口，并注册
            if(clazz.isAnnotationPresent(Service.class)){
                String serviceName = clazz.getAnnotation(Service.class).name();
                Object service;
                try {
                    service = clazz.getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                    throw new RpcException("服务注册---创建实例出错");
                }
                //没有指定名称时，使用接口全限定名称
                if("".equals(serviceName)){
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> anInterface : interfaces) {
                        log.info("注册服务:{}",anInterface.getCanonicalName());
                        publishService(service,anInterface.getCanonicalName());
                    }
                }else{
                    log.info("注册服务:{}",serviceName);
                    publishService(service,serviceName);
                }
            }
        }
    }
}
