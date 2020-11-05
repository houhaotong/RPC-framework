package com.hht.rpc.codec;

import com.hht.rpc.serializer.CommonSerializer;
import com.hht.rpc.serializer.JsonSerializer;
import com.hht.rpc.serializer.KryoSerializer;
import domain.RpcRequest;
import enums.PackageCode;
import enums.SerializerCode;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码格式 密码+序列化器号+数据类型+长度+数据
 * @author hht
 * @date 2020/11/3 22:07
 */
public class ObjEncoder extends MessageToByteEncoder {

    private final CommonSerializer serializer=new KryoSerializer();
    private static final int ENCODE_PWD = 0xDDD5232A;

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.writeInt(ENCODE_PWD);
        out.writeInt(SerializerCode.KRYO.getCode());
        if(msg instanceof RpcRequest){
            out.writeInt(PackageCode.REQUEST_PACK.getCode());
        }else{
            out.writeInt(PackageCode.RESPONSE_PACK.getCode());
        }
        byte[] bytes = serializer.serialize(msg);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
