package com.hht.rpc.registry.serverdiscover;

import exception.RpcException;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * zookeeper服务发现
 * @author hht
 * @date 2020/11/10 21:25
 */
@Slf4j
public class ZkServerDiscovery implements ServerDiscovery {

    private ZkClient zkClient;

    @Override
    public InetSocketAddress discover(String serviceName) {
        String servicePath = "/registry/" + serviceName;
        if (!zkClient.exists(servicePath)) {
            throw new RpcException("找不到对应节点:" + servicePath);
        }
        List<String> children = zkClient.getChildren(servicePath);
        if (children.size() == 0) {
            throw new RpcException("未找到address节点");
        }
        String addressPath;
        if (children.size() == 1) {
            addressPath = children.get(0);
            log.info("获取到唯一address节点地址:{}", addressPath);
        } else {
            //随机获取一个服务提供地址
            addressPath = children.get(ThreadLocalRandom.current().nextInt(children.size()));
            log.info("随机获取到一个address节点地址:{}", addressPath);
        }
        //完整的address节点的地址
        addressPath = servicePath + "/" + addressPath;
        //读取address结点的数据
        return zkClient.readData(addressPath);

    }
}
