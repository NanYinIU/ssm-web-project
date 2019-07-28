package com.nanyin.config.util;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public enum ResultMap {
    rows, total;
    public static Map<ResultMap,Object> generateInstance(Integer total, Collection<?> rows){
        HashMap<ResultMap,Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put(ResultMap.rows,rows);
        objectObjectHashMap.put(ResultMap.total,total);
        return objectObjectHashMap;
    }
}
