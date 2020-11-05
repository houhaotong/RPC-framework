package com.hht.rpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import domain.RpcRequest;
import domain.RpcResponse;
import enums.RpcError;
import exception.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author hht
 * @date 2020/11/5 15:59
 */
@Slf4j
public class KryoSerializer implements CommonSerializer {

    /**使用threadLocal，为每个线程提供各自特有的对象，保证线程安全*/
    private final ThreadLocal<Kryo> kryoThreadLocal= ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        //支持对象循环引用
        kryo.setReferences(true);
        //默认为false，不强制要求注册类（因为业务中有大量的class，难以一一的注册进来）
        kryo.setRegistrationRequired(false);
        //注册要序列化的类
        kryo.register(RpcRequest.class);
        kryo.register(RpcResponse.class);
        return kryo;
    });

    /**
     * 获取当前线程的kryo实例
     * @return kryo
     */
    public Kryo getKryo(){
        return kryoThreadLocal.get();
    }

    @Override
    public byte[] serialize(Object obj) {
        try(ByteArrayOutputStream stream=new ByteArrayOutputStream();
            Output output=new Output(stream)) {
            Kryo kryo = getKryo();
            //写入到kryo的输出流中,进行序列化
            kryo.writeClassAndObject(output, obj);
            output.flush();
            return stream.toByteArray();
        }catch (Exception e) {
            log.info("Kryo序列化出错", e);
            throw new RpcException(RpcError.BAD_SERIALIZE);
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try(ByteArrayInputStream stream=new ByteArrayInputStream(bytes);
            Input input=new Input(stream)) {
            Kryo kryo=getKryo();
            //从输入流中反序列化读取对象
            return kryo.readClassAndObject(input);
        } catch (IOException e) {
            log.error("Kryo反序列化出错",e);
            throw new RpcException(RpcError.BAD_DESERIALIZE);
        }
    }
}
