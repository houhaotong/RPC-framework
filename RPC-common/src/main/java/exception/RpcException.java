package exception;

import domain.RpcError;

/**
 * @author hht
 * @date 2020/10/30 23:48
 */
public class RpcException extends RuntimeException {

    public RpcException(RpcError error,String message){
        super("error:"+error+"detail:"+message);
    }
    public RpcException(RpcError error){
        super(error.getMessage());
    }
}
