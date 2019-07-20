package com.nanyin;

import org.apache.shiro.crypto.hash.SimpleHash;

public class test1 {

    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";//加密方式
        Object crdentials = "1";//密码原值
        Object salt = "1";//盐值
        int hashIterations = 1024;//加密1024次
        Object result = new SimpleHash(hashAlgorithmName,crdentials,salt,hashIterations);
        System.out.println(result);
    }

}
