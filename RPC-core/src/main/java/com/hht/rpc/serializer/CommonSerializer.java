package com.hht.rpc.serializer;

/**
 * 通用序列化器接口
 * @author hht
 * @date 2020/11/5 15:53
 */
public interface CommonSerializer {

    /**
     * 序列化
     * @param obj 序列化对象
     * @return 字节数组
     */
    public byte[] serialize(Object obj);

    /**
     * 反序列化字节数组
     * @param bytes 字节数组
     * @param clazz 需要反序列化的对象类型
     * @return 反序列化后的对象
     */
    public Object deserialize(byte[] bytes,Class<?> clazz);
}
