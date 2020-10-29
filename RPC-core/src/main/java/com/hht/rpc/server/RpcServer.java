package com.hht.rpc.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * 服务端，使用线程池分配线程进行注册服务
 * @author hht
 * @date 2020/10/28 20:35
 */
@Slf4j
public class RpcServer {

    private final ExecutorService threadPool;

    /** 初始化线程池 */
    public RpcServer(){
        //线程数
        int corePoolSize=5;
        //最大允许线程数
        int maximumPoolSize=10;
        //大于默认线程数时，空闲线程等待时间，会转换为纳秒
        long keepAliveTime=60;
        //单位设置为秒
        TimeUnit unit=TimeUnit.SECONDS;
        //保存任务的队列,容量设为50
        BlockingQueue<Runnable> workQueue=new ArrayBlockingQueue<Runnable>(50);
        ThreadFactory threadFactory=Executors.defaultThreadFactory();
        threadPool=new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue,threadFactory);
    }

    /**
     * 注册服务到指定端口
     * @param service 需要被注册的服务
     * @param port 端口号
     */
    public void register(Object service,int port){
        try(ServerSocket serverSocket=new ServerSocket(port)){
            log.info("服务器等待连接.....");
            Socket socket;
            while ((socket=serverSocket.accept())!=null){
                log.info("连接成功！客户端ip为:"+socket.getInetAddress());
                threadPool.execute(new WorkThread(socket,service));
            }
        }catch (IOException e){
            log.warn("连接发生错误！");
        }
    }
}
