package com.atguigu.ssyx.common.result;


import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String message;

    private T data;

    private Result(){

    }

    //设置数据，返回对象的方法
    public static<T> Result<T> build(T data, ResultCodeEnum resultCodeEnum){
        Result<T> result = new Result<>();
        if (data!=null){
            result.setData(data);
        }
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());

        return result;
    }

    //返回成功的方法
    public static<T> Result<T> ok(T data){
        Result<T> result = build(data, ResultCodeEnum.SUCCESS);
        return result;
    }

    //返回失败的方法
    public static<T> Result<T> fail(T data){
        Result<T> result = build(data, ResultCodeEnum.FAIL);
        return result;
    }
}
