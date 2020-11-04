package com.hht.rpc.server.socket;

import com.hht.rpc.registry.ServerRegistry;
import com.hht.rpc.server.RequestHandler;
import com.hht.rpc.server.RpcServer;
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
public class SocketServer implements RpcServer {

    private final ExecutorService threadPool;

    /** 初始化线程池 */
    public SocketServer(){
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
     * 开启服务
     * @param registry 注册的服务表
     * @param port 端口号
     */
    @Override
    public void start(ServerRegistry registry, int port){
        try(ServerSocket serverSocket=new ServerSocket(port)){
            log.info("服务器等待连接.....");
            Socket socket;
            while ((socket=serverSocket.accept())!=null){
                log.info("连接成功！客户端ip为:"+socket.getInetAddress());
                threadPool.execute(new WorkThread(socket,registry,new RequestHandler()));
            }
        }catch (IOException e){
            log.warn("连接发生错误！");
        }
    }
}
