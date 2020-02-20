package com.nanyin.config.exceptions;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException() {
        super("Token已过期！请重新登陆");
    }
}
