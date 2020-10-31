package com.hht.rpc.server;

import com.hht.rpc.registry.ServerRegistry;
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

    private final ServerRegistry registry;

    private final RequestHandler handler;

    public WorkThread(Socket socket,ServerRegistry registry,RequestHandler handler){
        this.socket=socket;
        this.registry=registry;
        this.handler=handler;
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
            Object service = registry.getServer(request.getInterfaceName());
            RpcResponse res = handler.handle(request,service);
            outputStream.writeObject(res);
            outputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            log.warn("调用服务失败！");
        }
    }
}
