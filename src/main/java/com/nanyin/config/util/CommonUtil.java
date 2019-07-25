package com.nanyin.config.util;

import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.by;

public class CommonUtil {

    public static Sort cending(String direction,String... propertis) {
       return new Sort(Sort.Direction.fromString(direction),propertis);
    }
}
