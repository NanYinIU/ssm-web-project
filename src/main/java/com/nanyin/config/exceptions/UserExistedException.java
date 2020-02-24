package com.nanyin.config.exceptions;

public class UserExistedException extends RuntimeException{
    public UserExistedException() {
        super("用户已存在！请修改用户名，重新提交！");
    }
}
