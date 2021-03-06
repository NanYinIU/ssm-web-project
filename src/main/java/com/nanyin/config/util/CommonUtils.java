package com.nanyin.config.util;

import java.util.*;

import com.google.common.base.Strings;
import com.nanyin.config.exceptions.CheckException;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.text.SimpleDateFormat;

public class CommonUtils {

    private static MessageSource resources;

    private static Sort sending(String direction, String... propertis) {
       return new Sort(Sort.Direction.fromString(direction),propertis);
    }

    public static PageRequest pageRequest(Integer offset, Integer limit, String order, String... properties){
        return new PageRequest((offset/limit),limit, CommonUtils.sending(order,properties));
    }

    /**
     * 使用 时间戳-随机uuid的方式构造 11位的字符串进行对request的跟踪。
     * 应用于多应用，或多动作之间的日志跟踪
     **/
    public static String generateRequestUid(){
        Date date = new Date();
        String dateString = new SimpleDateFormat("hhmmss-SSS").format(date);
        return dateString+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0,10);
    }

    public static void setResources(MessageSource resources) {
        CommonUtils.resources = resources;
    }

    /**
     * 检查条件，如果不符合条件抛出异常
     **/
    public static void check(boolean condition, String msgKey, Object... args) {
        if (!condition) {
            fail(msgKey, args);
        }
    }

    private static void fail(String msgKey, Object... args) {
        Locale locale = MDCUtil.getLocale();
        if(locale==null){
            locale = new Locale("zh_CN");
        }
        String msg = "";
        try{
            msg = resources.getMessage(msgKey, args, locale);
        }catch (NoSuchMessageException npe){
            msg = msgKey;
        }
        if("".equals(msg)){
            msg = msgKey;
        }
        throw new CheckException(msg);
    }

    /**
     * object 是否为空
     **/
    public static boolean isNull(Object o){
        return !Optional.ofNullable(o).isPresent();
    };

    public static boolean isNotNull(Object o){
        return Optional.ofNullable(o).isPresent();
    }

}
