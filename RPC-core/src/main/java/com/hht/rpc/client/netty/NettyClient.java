package com.hht.rpc.client.netty;

import com.hht.rpc.client.RpcClient;
import domain.RpcRequest;
import domain.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hht
 * @date 2020/11/3 16:11
 */
@Slf4j
public class NettyClient implements RpcClient {

    @Override
    public Object sendRequest(RpcRequest request, String host, int port) {
        EventLoopGroup group=new NioEventLoopGroup();
        Bootstrap b=new Bootstrap();
        try {
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new NettyClientInitializer());
            ChannelFuture future = b.connect(host, port).sync();
            Channel ch = future.channel();
            ch.writeAndFlush(request);
            ch.closeFuture().sync();
            //从channel中获取response的值
            AttributeKey<RpcResponse> key=AttributeKey.valueOf("rpcResponse");
            return ch.attr(key).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return null;
    }
}
