package com.jasonchow.result;

import lombok.Data;

@Data
public class Result<T> {

    private Integer code; //// 编码，1表示成功，0表示失败
    private String msg;  ///// 错误信息，没错就会null
    private T data;  //// 响应数据

    ///// 带响应数据的成功响应
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode(1);
        result.setData(data);
        return result;
    }

    //// 无数据的成功响应
    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.setCode(1);
        return result;
    }

    //// 报错响应
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<T>();
        result.setCode(0);
        result.setMsg(msg);
        return result;
    }
}
