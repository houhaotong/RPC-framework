package com.hht.rpc.server.netty;

import com.hht.rpc.codec.ObjDecoder;
import com.hht.rpc.codec.ObjEncoder;
import domain.RpcRequest;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * netty服务器channel初始化配置
 * @author hht
 * @date 2020/11/3 16:49
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new ObjDecoder())
                .addLast(new ObjEncoder())
                .addLast(new NettyServerHandler());
    }
}
