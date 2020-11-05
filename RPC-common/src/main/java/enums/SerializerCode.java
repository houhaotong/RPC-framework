package enums;

/**
 * 存储序列化器代码
 * @author hht
 * @date 2020/11/5 15:48
 */
public enum SerializerCode {

    JSON(1),
    KRYO(2);

    SerializerCode(int code){
        this.code=code;
    }
    private final int code;

    public int getCode(){
        return code;
    }
}
