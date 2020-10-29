package com.hht.rpc.client;

import domain.RpcRequest;
import domain.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

/**
 * @author hht
 * @date 2020/10/28 15:35
 */
@Slf4j
public class RpcClient {

    public Object sendRequest(RpcRequest request,String host, int port){
        //通过socket与服务端建立连接
        try (Socket socket=new Socket(host, port)){
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            log.info("发送请求");
            //序列化request请求对象到输出流，并强制输出
            outputStream.writeObject(request);
            outputStream.flush();
            //从输入流中读取服务端所写入的对象
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.warn("调用请求发送失败！");
            return null;
        }
    }
}
