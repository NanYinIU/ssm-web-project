package com.nanyin.controller;

import com.nanyin.config.util.Tools;
import com.nanyin.entity.result.Result;
import com.nanyin.config.enums.ResultCodeEnum;
import lombok.Data;


public class BasicController {
    private static final long serialVersionUID = -4387601934436355246L;
    protected Result result;

    protected Result setData(Object data){
        result.setData(data);
        return result;
    }

    protected Result setMessage(String message){
        result.setMessage(message);
        return result;
    }
    protected Result setCode(ResultCodeEnum code){
        result.setCode(code);
        return result;
    }


    @Data
    class Page{
        protected int offset;
        protected int limit;
        protected String order ;

        protected long total;
        protected String sort;


        public Page(int offset, int limit, String order) {
            this.offset = offset;
            this.limit = limit==0?10:limit;
            this.order = Tools.isNotNull(order)?order:"asc";
        }

    }
}
