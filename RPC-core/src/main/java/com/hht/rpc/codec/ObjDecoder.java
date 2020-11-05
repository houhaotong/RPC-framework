package com.hht.rpc.codec;

import com.hht.rpc.serializer.CommonSerializer;
import com.hht.rpc.serializer.JsonSerializer;
import com.hht.rpc.serializer.KryoSerializer;
import domain.RpcRequest;
import domain.RpcResponse;
import enums.PackageCode;
import enums.RpcError;
import exception.RpcException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author hht
 * @date 2020/11/3 22:38
 */
@Slf4j
public class ObjDecoder extends ByteToMessageDecoder {

    private static final int ENCODE_PWD = 0xDDD5232A;

    private CommonSerializer serializer;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes()<4){
            return;
        }
        int pwd=in.readInt();
        if (pwd!=ENCODE_PWD){
            log.error("---不支持的数据包");
            throw new RpcException(RpcError.PACKAGE_NOT_SUPPORT);
        }
        int serializerCode=in.readInt();
        if(serializerCode==1){
            serializer=new JsonSerializer();
        }if(serializerCode==2){
            serializer=new KryoSerializer();
        }else{
            log.error("---decode错误",new RpcException(RpcError.NOT_FOUND_SERIALIZER));
        }
        int packageCode = in.readInt();
        Class<?> clazz;
        if(packageCode== PackageCode.REQUEST_PACK.getCode()){
            clazz= RpcRequest.class;
        }else if(packageCode==PackageCode.RESPONSE_PACK.getCode()){
            clazz= RpcResponse.class;
        }else{
            log.error("---数据包类型未识别");
            throw new RpcException(RpcError.PACKAGE_TYPE_BAD);
        }
        int length=in.readInt();
        byte[] bytes=new byte[length];
        in.readBytes(bytes);
        Object obj = serializer.deserialize(bytes, clazz);
        out.add(obj);
    }
}
