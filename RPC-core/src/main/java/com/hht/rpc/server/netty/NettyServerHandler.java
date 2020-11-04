package com.hht.rpc.server.netty;

import com.hht.rpc.registry.DefaultServerRegistry;
import com.hht.rpc.registry.ServerRegistry;
import com.hht.rpc.server.RequestHandler;
import domain.RpcRequest;
import domain.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hht
 * @date 2020/11/3 16:11
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private final ServerRegistry registry=new DefaultServerRegistry();
    private final RequestHandler handler=new RequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        log.info("---远程调用:"+request.getMethodName());
        Object service = registry.getService(request.getInterfaceName());
        RpcResponse response = handler.handle(request, service);
        ctx.writeAndFlush(response);
        ctx.channel().close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("---有客户端连接:"+ctx.channel().localAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
