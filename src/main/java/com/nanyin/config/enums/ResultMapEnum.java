package com.nanyin.config.enums;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public enum ResultMapEnum {
    rows, total;
    public static Map<ResultMapEnum,Object> generateInstance(Integer total, Collection<?> rows){
        HashMap<ResultMapEnum,Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put(ResultMapEnum.rows,rows);
        objectObjectHashMap.put(ResultMapEnum.total,total);
        return objectObjectHashMap;
    }
}
