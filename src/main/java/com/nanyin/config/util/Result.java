package com.nanyin.config.util;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@Data
public class Result<T> implements Serializable {

    public static final int NO_LOGIN = -1;

    public static final int SUCCESS = 0;

    public static final int FAIL = 1;

    public static final int NO_PERMISSION = 2;

    private String msg = "success";

    private int code = SUCCESS;

    private T data;

    public Result() {
        super();
    }

    public Result(T data) {
        super();
        this.data = data;
    }

    Result(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = FAIL;
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


}
