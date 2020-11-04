package domain;

import enums.ResponseCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 响应内容
 * @author hht
 * @date 2020/10/28 15:08
 */
@Data
public class RpcResponse<T> implements Serializable {

    /** 状态码 */
    private Integer code;

    /** 响应数据 */
    private T data;

    /** 响应信息 */
    private String message;

    public static <T>RpcResponse<T> success(T data){
        RpcResponse<T> response=new RpcResponse<>();
        response.message= ResponseCode.SUCCESS.getMessage();
        response.code=ResponseCode.SUCCESS.getCode();
        response.data=data;
        return response;
    }

    public static <T>RpcResponse<T> fail(ResponseCode code){
        RpcResponse<T> response=new RpcResponse<>();
        response.message=code.getMessage();
        response.code=code.getCode();
        return response;
    }
}
