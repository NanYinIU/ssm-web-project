package com.nanyin.config.exceptions.userException;

public class UserBlockedException extends RuntimeException {
    public UserBlockedException() {
        super("用户已经被封锁！");
    }
}
