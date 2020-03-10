package com.nanyin.config.util;

import com.nanyin.config.enums.ResultCodeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class Result<T> implements Serializable {

    /**
     * 定义返回信息，默认success
     */
    private String message = "success";

    /**
     * 定义返回code值，默认 SUCCESS
     */
    private int code = ResultCodeEnum.SUCCESS.getCode();

    /**
     * 定义返回数据
     */
    private T data;


    public Result() {
        super();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    /**
     * 使用setCode方法会默认将ResultCodeEnum中的code和message赋予给result
     * 如果默认的message不符合情况，可使用setMessage方法再次设置一次message
     * @param code
     */
    public void setCode(ResultCodeEnum code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result(T data) {
        super();
        this.data = data;
    }

    public Result(String message, int code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

}
