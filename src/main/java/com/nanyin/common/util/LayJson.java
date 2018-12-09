package com.nanyin.common.util;

import org.apache.shiro.crypto.hash.Hash;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: NanYin
 * @Date: 12/8/18 21:44
 * @Description:
 */
public class LayJson {

    public static Object code;
    public static Object count;
    public static List<Object> data;
    public static Object msg;

    public static Object getCode() {
        if (code==null) code="";
        return code;
    }

    public static void setCode(Object code) {
        LayJson.code = code;
    }

    public static Object getCount() {
        if (count==null) count="";
        return count;
    }

    public static void setCount(Object count) {
        LayJson.count = count;
    }

    public static List<Object> getData() {
        return data;
    }

    public static void setData(List<Object> data) {
        LayJson.data = data;
    }

    public static Object getMsg() {
        if (msg==null) msg="";
        return msg;
    }

    public static void setMsg(Object msg) {
        LayJson.msg = msg;
    }

    public static Map<Object,Object> Map(){
        Map<Object,Object> map = new HashMap<Object, Object>();
        map.put("code",getCode());
        map.put("count",getCount());
        map.put("data",getData());
        map.put("msg",getMsg());
        return map;
    }

}