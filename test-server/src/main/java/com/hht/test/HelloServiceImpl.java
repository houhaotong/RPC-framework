package com.hht.test;

import com.hht.rpc.HelloObj;
import com.hht.rpc.HelloService;
import com.hht.rpc.anno.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hht
 * @date 2020/10/28 22:14
 */
@Slf4j
@Service(name = "helloService")
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(HelloObj helloObj) {
        log.info(this.getClass().getName()+"被调用，调用者："+helloObj);
        return "Hello,Id:"+helloObj.getId()+",message:"+helloObj.getMessage();
    }
}
