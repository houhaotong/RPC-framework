package com.hht.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author hht
 * @date 2020/10/27 20:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloObj implements Serializable {

    private Integer id;
    private String message;

}
