package com.hht.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hht
 * @date 2020/10/27 20:32
 */
@Data
@AllArgsConstructor
public class HelloObj implements Serializable {

    private Integer id;
    private String message;

}
