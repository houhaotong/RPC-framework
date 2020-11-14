package com.hht.rpc.hooks;

import com.hht.rpc.utils.NacosUtil;

/**
 * 关闭时的钩子方法
 * @author hht
 * @date 2020/11/14 22:52
 */
public class closeHook {
    private closeHook(){

    }
    private static final closeHook CLOSE_HOOK=new closeHook();

    public static closeHook getInstance(){
        return CLOSE_HOOK;
    }

    /**
     * 添加一个关闭时的钩子
     */
    public void addClearHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                NacosUtil.removeInstances();
            }
        });
    }
}
