package com.nanyin.config.exceptions.tokenException;

public class TokenParseException extends RuntimeException {
    public TokenParseException() {
        super("Token 解析异常！");
    }
}
