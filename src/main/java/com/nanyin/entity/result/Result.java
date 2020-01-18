package com.nanyin.entity.result;

import com.nanyin.config.enums.ResultCodeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@Data
public class Result<T> implements Serializable {

    private String message = "success";

    private ResultCodeEnum code = ResultCodeEnum.SUCCESS;

    private T data;

    private int total;

    public Result() {
        super();
    }

    public Result(T data) {
        super();
        this.data = data;
    }

    Result(Throwable e) {
        super();
        this.message = e.toString();
        this.code = ResultCodeEnum.FAIL;
    }

    public Result(String msg, ResultCodeEnum code, T data) {
        this.message = msg;
        this.code = code;
        this.data = data;
    }

    public static Result resultInstance(){
        return new Result<>();
    }

    public static Result resultInstance(Collection<?> data){
        return new Result<Collection>(data);
    }

    public static Result resultInstance(Map<?,?> data){
        return new Result<Map>(data);
    }

    public static Result resultInstance(Throwable e){
        return new Result(e);
    }

    public static Result resultInstance(Object data){
        return new Result<Object>(data);
    }

    public static Result resultInstance(ResultCodeEnum code, String message, Object data){
        return  new Result<Object>(message,code,data);
    }


}
