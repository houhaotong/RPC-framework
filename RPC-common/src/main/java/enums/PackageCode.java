package enums;

/**
 * 发送的数据包类型代码
 * @author hht
 * @date 2020/11/3 22:12
 */
public enum PackageCode {

    REQUEST_PACK(1),
    RESPONSE_PACK(2);

    PackageCode(int code){
        this.code=code;
    }
    private int code;

    public int getCode() {
        return code;
    }
}
