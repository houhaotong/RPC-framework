package com.hht.rpc.utils;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import enums.RegistryURL;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * nacos的工具类
 *
 * @author hht
 * @date 2020/11/10 17:00
 */
@Slf4j
public class NacosUtil {

    private static final NamingService namingService;

    private static Set<String> serviceNames = new HashSet<>();

    private static String host;
    private static int port;

    static {
        namingService = getNamingService();
    }

    public static NamingService getNamingService() {
        try {
            NamingService namingService = NamingFactory.createNamingService(RegistryURL.NACOSADDR.getUrl());
            return namingService;
        } catch (NacosException e) {
            e.printStackTrace();
            log.error("连接nacos错误！", e);
        }
        return null;
    }

    public static void register(String serviceName, InetSocketAddress address) throws NacosException {
        host = address.getHostName();
        port = address.getPort();
        namingService.registerInstance(serviceName, host, port);
        serviceNames.add(serviceName);
    }

    public static List<Instance> getAllInstances(String serviceName) {
        NamingService namingService = getNamingService();
        try {
            return namingService.getAllInstances(serviceName);
        } catch (NacosException e) {
            e.printStackTrace();
            log.error("获取服务实例异常");
        }
        return new ArrayList<>();
    }

    public static void removeInstances() {
        for (String serviceName : serviceNames) {
            try {
                log.info("服务已注销");
                namingService.deregisterInstance(serviceName, host, port);
            } catch (NacosException e) {
                e.printStackTrace();
                log.error("服务注销失败");
            }
        }
    }

}
