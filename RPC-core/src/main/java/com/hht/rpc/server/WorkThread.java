package com.hht.rpc.server;

import domain.ResponseCode;
import domain.RpcRequest;
import domain.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * 工作线程
 * @author hht
 * @date 2020/10/28 20:43
 */
@Slf4j
public class WorkThread implements Runnable {

    private Socket socket;

    private Object service;

    public WorkThread(Socket socket,Object service){
        this.socket=socket;
        this.service=service;
    }

    /**
     * 先获取请求内容，然后通过反射调用方法然后返回结果对象
     */
    @Override
    public void run() {
        //从输入流中读取对象（反序列化），写入对象到输出流（序列化）

        try (ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream())){
            RpcRequest request=(RpcRequest) inputStream.readObject();
            //反射调用方法并返回对象,然后序列化写入输出流
            Method method=service.getClass().getMethod(request.getMethodName(),request.getParameterTypes());
            Object res = method.invoke(service, request.getParameters());
            outputStream.writeObject(RpcResponse.success(res));
            outputStream.flush();
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.warn("调用服务失败！");
        }
    }
}
