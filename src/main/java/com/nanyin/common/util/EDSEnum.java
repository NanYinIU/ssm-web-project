package com.nanyin.common.util;

/**
 * @Auther: NanYin
 * @Date: 11/10/18 18:32
 * @Description: 加密解密的枚举类
 */
public enum EDSEnum {
    DES("DES"),SHA1PRNG("SHA1PRNG"),CHARTSET("UTF-8"),KEY_STR("NanYin");

    private String name;

    EDSEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}