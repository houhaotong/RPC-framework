package com.hht.rpc.server.netty;

import com.hht.rpc.hooks.closeHook;
import com.hht.rpc.provider.ServiceProviderImpl;
import com.hht.rpc.registry.ServerRegistry;
import com.hht.rpc.server.AbstractRpcServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author hht
 * @date 2020/11/3 16:11
 */
@Slf4j
public class NettyServer extends AbstractRpcServer {

    public NettyServer(String host,int port,ServerRegistry registry){
        this.host=host;
        this.port=port;
        this.serverRegistry=registry;
        this.serviceProvider=new ServiceProviderImpl();
        //初始化时扫描服务
        scanServices();
    }

    @Override
    public void start() {
        //启动时添加钩子
        closeHook.getInstance().addClearHook();
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap b=new ServerBootstrap();
        try {
            b.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new NettyServerInitializer());
            ChannelFuture ch = b.bind(port).sync();
            log.info("---服务器启动成功!");
            ch.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("---服务器错误!");
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
