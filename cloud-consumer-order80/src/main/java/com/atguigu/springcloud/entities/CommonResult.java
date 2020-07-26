package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Fred Zhang
 * @create 2020-07-25 11:54 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T>{
    private Integer code;  //404 not_found 200  500
    private String message;
    private T data;

    public CommonResult(Integer code,String message){

        this(code,message,null);

    }
}
