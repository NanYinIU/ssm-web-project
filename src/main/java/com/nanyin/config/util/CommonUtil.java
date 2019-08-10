package com.nanyin.config.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.by;

public class CommonUtil {

    private static Sort cending(String direction,String... propertis) {
       return new Sort(Sort.Direction.fromString(direction),propertis);
    }

    public static PageRequest pageRequest(Integer offset, Integer limit, String order, String... properties){
        return new PageRequest((offset/limit),limit, CommonUtil.cending(order,properties));
    }
}
