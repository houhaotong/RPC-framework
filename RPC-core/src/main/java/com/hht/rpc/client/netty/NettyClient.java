package com.hht.rpc.client.netty;

import com.hht.rpc.client.RpcClient;
import com.hht.rpc.registry.ServerRegistry;
import com.hht.rpc.registry.ZookeeperServerRegistry;
import com.hht.rpc.registry.serverdiscover.NacosServerDiscovery;
import com.hht.rpc.registry.serverdiscover.ServerDiscovery;
import domain.RpcRequest;
import domain.RpcResponse;
import enums.LoadBalanceType;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;


import java.net.InetSocketAddress;

/**
 * @author hht
 * @date 2020/11/3 16:11
 */
@Slf4j
public class NettyClient implements RpcClient {

    private final ServerDiscovery discovery;

    public NettyClient(ServerDiscovery discovery){
        this.discovery=discovery;
    }

    @Override
    public Object sendRequest(RpcRequest request) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        InetSocketAddress address = discovery.discover(request.getInterfaceName());
        String host = address.getHostName();
        int port = address.getPort();
        log.info("从{}:{}调用服务",host,port);
        try {
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new NettyClientInitializer());
            ChannelFuture future = b.connect(host, port).sync();
            Channel ch = future.channel();
            ch.writeAndFlush(request);
            ch.closeFuture().sync();
            //从channel中获取response的值
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
            return ch.attr(key).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return null;
    }
}
