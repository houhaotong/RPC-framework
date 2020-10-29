package domain;

/**
 * @author hht
 * @date 2020/10/28 15:16
 */
public enum ResponseCode {

    SUCCESS(200,"成功"),
    FAIL(400,"失败"),
    NOT_FOUND_METHOD(500,"未找到指定方法"),
    NOT_FOUND_CLASS(501,"未找到对应实现类");

    ResponseCode(Integer code,String message){
        this.code=code;
        this.message=message;
    }

    /** 状态码 */
    private final Integer code;

    /** 响应信息 */
    private final String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
