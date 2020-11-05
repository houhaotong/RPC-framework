package com.hht.rpc.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.RpcRequest;


/**
 * 序列化成jsonByte以及反序列化
 * @author hht
 * @date 2020/11/3 22:26
 */
public class JsonSerializer implements CommonSerializer {

    @Override
    public byte[] serialize(Object msg){
        return JSON.toJSONBytes(msg);
    }

    /**
     * 注意这里，反序列化后，其中的参数类型会变成 {@link com.alibaba.fastjson.JSONObject}，需要再次根据请求中指定的参数类型进行类型转换.
     * @param bytes
     * @param clazz
     * @return
     */
    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz){
        Object obj= JSON.parseObject(bytes, clazz);
        if(obj instanceof RpcRequest){
            RpcRequest request=(RpcRequest) obj;
            Object[] parameters = request.getParameters();
            Class<?>[] types = request.getParameterTypes();
            Object[] newObj=new Object[parameters.length];
            for(int i=0;i<parameters.length;i++){
                String jsonString = JSON.toJSONString(parameters[i]);
                newObj[i] = JSON.parseObject(jsonString, types[i]);
            }
            request.setParameters(newObj);
        }
        return obj;
    }
}
