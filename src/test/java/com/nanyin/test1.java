package com.nanyin;

import com.nanyin.config.util.CommonUtil;
import com.nanyin.config.util.MDCUtil;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

public class test1 {

    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";//加密方式
        Object crdentials = "1";//密码原值
        Object salt = "1";//盐值
        int hashIterations = 1024;//加密1024次
        Object result = new SimpleHash(hashAlgorithmName,crdentials,salt,hashIterations);
        System.out.println("web".getBytes());
    }

    @Test
    public void test2(){
        Date date = new Date();
        String dateString = new SimpleDateFormat("MMddhhmmss").format(date);
        System.out.println(dateString+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0,10));
        System.out.println(MDCUtil.getLocale().toString());
        System.out.println(CommonUtil.isNull(null));
    }


}
