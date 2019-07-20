package com.nanyin.config.exceptions;

public class UserIsBlockException extends RuntimeException {
    public UserIsBlockException() {
        super("用户已经被封锁！");
    }
}
