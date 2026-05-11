package com.atguigu.ssyx.common.exception;

import com.atguigu.ssyx.common.result.ResultCodeEnum;
import lombok.Data;

@Data
public class SsyxException extends RuntimeException{
    private Integer code;

    public SsyxException(String message, Integer code){
        super(message);
        this.code = code;
    }

    //接受枚举类型对象
    public SsyxException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
}
