package domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 请求协议
 * @author hht
 * @date 2020/10/27 22:43
 */
@Data
public class RpcRequest implements Serializable {

    /** 待调用接口名 */
    private String interfaceName;

    /** 要调用的方法名 */
    private String methodName;

    /** 传递的参数 */
    private Object[] parameters;

    /** 传递的参数列表 */
    private Class<?>[] parameterTypes;

    private RpcRequest(Builder builder){
        this.interfaceName=builder.interfaceName;
        this.methodName=builder.methodName;
        this.parameters=builder.parameters;
        this.parameterTypes=builder.parameterTypes;
    }

    public static class Builder{
        /** 待调用接口名 必须 */
        private String interfaceName;

        /** 要调用的方法名 必须 */
        private String methodName;

        /** 传递的参数 */
        private Object[] parameters;

        /** 传递的参数列表 */
        private Class<?>[] parameterTypes;

        public Builder(){

        }

        public Builder interfaceName(String interfaceName){
            this.interfaceName=interfaceName;
            return this;
        }

        public Builder methodName(String methodName){
            this.methodName=methodName;
            return this;
        }

        public Builder parameters(Object[] parameters){
            this.parameters=parameters;
            return this;
        }

        public Builder parameterTypes(Class<?>[] types){
            this.parameterTypes=types;
            return this;
        }

        public RpcRequest build(){
            return new RpcRequest(this);
        }
    }
}
