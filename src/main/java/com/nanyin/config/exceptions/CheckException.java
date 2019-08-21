package com.nanyin.config.exceptions;

/**
 * 检查异常
 * @Author nanyin
 * @Date 13:50 2019-08-20
 **/
public class CheckException extends RuntimeException {
    public CheckException(String message) {
        super(message);
    }
}
