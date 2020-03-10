package com.nanyin.config.enums;

public enum ResultCodeEnum {
    /**
     * illegal token : code : 50008
     */
    ILLEGAL_TOKEN(50008,"illegal token"),
    /**
     * TOKEN_EXPIRED : code : 50014
     */
    TOKEN_EXPIRED(50014,"token expired"),

    /**
     * SUCCESS : code : 20000
     */
    SUCCESS(20000,"success"),
    /**
     * FAIL : code : 10000
     */
    FAIL(10000,"fail"),
    /**
     * NO_PERMISSION: code : 10005
     */
    NO_PERMISSION(10005,"no permission"),
    /**
     * WRONG_USERNAME_OR_PASSWORD : code :10006
     */
    WRONG_USERNAME_OR_PASSWORD(10006,"wrong username or password");

    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
