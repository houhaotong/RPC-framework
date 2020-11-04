package com.hht.rpc.server;

import enums.ResponseCode;
import domain.RpcRequest;
import domain.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author hht
 * @date 2020/10/31 21:51
 */
@Slf4j
public class RequestHandler {

    public RpcResponse handle(RpcRequest request,Object service) {
        RpcResponse result=null;
        try {
            result=invokeTargetMethod(request,service);
            log.info("正在调用方法--{}",request.getMethodName());
        } catch ( InvocationTargetException | IllegalAccessException e) {
            log.warn("{}--方法调用出错！",request.getMethodName());
            return RpcResponse.fail(ResponseCode.BAD_INVOKE);
        }
        return result;
    }

    private RpcResponse invokeTargetMethod(RpcRequest request,Object service) throws InvocationTargetException, IllegalAccessException {
        Method method;
        try {
             method= service.getClass().getMethod(request.getMethodName(), request.getParameterTypes());
        } catch (NoSuchMethodException e) {
            log.warn("未找到方法--{}",request.getMethodName());
            return RpcResponse.fail(ResponseCode.NOT_FOUND_METHOD);
        }
        Object result = method.invoke(service, request.getParameters());
        return RpcResponse.success(result);
    }
}
