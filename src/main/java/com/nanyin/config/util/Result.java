package com.nanyin.config.util;

import com.nanyin.config.enums.ResultCodeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@Data
public class Result<T> implements Serializable {

    /**
     * 定义返回信息，默认success
     */
    private String message = "success";

    /**
     * 定义返回code值，默认 SUCCESS
     */
    private ResultCodeEnum code = ResultCodeEnum.SUCCESS;

    /**
     * 定义返回数据
     */
    private T data;


    public Result() {
        super();
    }

    public Result(T data) {
        super();
        this.data = data;
    }

    public Result(String message, ResultCodeEnum code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

}
