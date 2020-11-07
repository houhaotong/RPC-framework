package com.hht.rpc.client;

import domain.RpcRequest;

/**
 * @author hht
 * @date 2020/10/31 22:28
 */
public interface RpcClient {

    public Object sendRequest(RpcRequest request);
}
