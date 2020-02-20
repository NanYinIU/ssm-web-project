package com.nanyin.config.enums;

public enum ResultCodeEnum {
    NO_LOGIN(-1),SUCCESS(0),FAIL(1),NO_PERMISSION(-2),WRONG_USERNAME_OR_PASSWORD(-3);
    int code;
    ResultCodeEnum(int code) {
        this.code = code;
    }
}
