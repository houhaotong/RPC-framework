package com.hht.rpc.client.netty;

import com.hht.rpc.codec.ObjDecoder;
import com.hht.rpc.codec.ObjEncoder;
import domain.RpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author hht
 * @date 2020/11/3 20:41
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new ObjDecoder())
                .addLast(new ObjEncoder())
                .addLast(new NettyClientHandler());
    }
}
