package com.nanyin.config.exceptions.tokenException;

public class TokenEmptyException extends RuntimeException {
    public TokenEmptyException() {
        super("Token不存在！");
    }
}
