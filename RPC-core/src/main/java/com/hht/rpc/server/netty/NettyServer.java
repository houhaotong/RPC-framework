package com.hht.rpc.server.netty;

import com.hht.rpc.registry.ServerRegistry;
import com.hht.rpc.server.RpcServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author hht
 * @date 2020/11/3 16:11
 */
@Slf4j
public class NettyServer implements RpcServer {

    @Override
    public void start(ServerRegistry registry, int port) {

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
