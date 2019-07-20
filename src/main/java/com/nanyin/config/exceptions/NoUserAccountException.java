package com.nanyin.config.exceptions;

public class NoUserAccountException extends RuntimeException {
    public NoUserAccountException() {
        super("不存在用户！");
    }
}
