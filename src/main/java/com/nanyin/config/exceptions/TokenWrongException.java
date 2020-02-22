package com.nanyin.config.exceptions;

public class TokenWrongException extends RuntimeException {
    public TokenWrongException() {
        super("用户名或密码错误！");
    }
}
