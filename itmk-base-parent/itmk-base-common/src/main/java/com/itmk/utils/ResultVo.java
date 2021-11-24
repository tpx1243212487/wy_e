package com.itmk.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data  //自动生成 get 和set方法
@AllArgsConstructor  //生成构造函数
public class ResultVo<T> {
    private String msg;
    private int code;
    private T data;
}