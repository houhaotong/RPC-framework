package domain;

/**
 * @author hht
 * @date 2020/10/30 23:54
 */
public enum RpcError {

    NOT_FOUND("未找到对应的服务"),
    NOT_IMPLEMENTS_INTERFACES("服务没有实现任何接口"),
    BAD_SERVER("服务器异常");


    RpcError(String message){
        this.message=message;
    }

    private String message;

    public String getMessage() {
        return message;
    }
}
