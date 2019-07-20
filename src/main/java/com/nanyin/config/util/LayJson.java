package com.nanyin.config.util;

//import org.apache.shiro.crypto.hash.Hash;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: NanYin
 * @Date: 12/8/18 21:44
 * @Description:
 */
public class LayJson {

    private  Object code;
    private  Object count;
    private  List<Object> data;
    private  Object msg;

    public  Object getCode() {
        if (code==null) code="";
        return code;
    }

    public  void setCode(Object code) {
        this.code = code;
    }

    public  Object getCount() {
        if (count==null) count="";
        return count;
    }

    public  void setCount(Object count) {
        this.count = count;
    }

    public  List<Object> getData() {
        return data;
    }

    public  void setData(List<Object> data) {
        this.data = data;
    }

    public  Object getMsg() {
        if (msg==null) msg="";
        return msg;
    }

    public  void setMsg(Object msg) {
        this.msg = msg;
    }

    public Map<Object,Object> Map(){
        Map<Object,Object> map = new HashMap<Object, Object>();
        map.put("code",getCode());
        map.put("count",getCount());
        map.put("data",getData());
        map.put("msg",getMsg());
        return map;
    }
}