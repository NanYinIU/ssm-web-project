package com.nanyin.config.util;

import org.apache.shiro.codec.CodecException;
import org.apache.shiro.crypto.UnknownAlgorithmException;
import org.apache.shiro.crypto.hash.SimpleHash;

public class BasicHash extends SimpleHash {
    public BasicHash(String algorithmName) {
        super(algorithmName);
    }

    public BasicHash(String algorithmName, Object source) throws CodecException, UnknownAlgorithmException {
        super(algorithmName, source);
    }

    public BasicHash(String algorithmName, Object source, Object salt) throws CodecException, UnknownAlgorithmException {
        super(algorithmName, source, salt);
    }

    public BasicHash(String algorithmName, Object source, Object salt, int hashIterations) throws CodecException, UnknownAlgorithmException {
        super(algorithmName, source, salt, hashIterations);
    }

    @Override
    protected byte[] objectToBytes(Object o) {
        if(o.getClass().equals(int.class) || o.getClass().equals(Integer.class)){
            Integer result = (int) o;
            return new byte[]{result.byteValue()};
        }else{
            String result = (String) o;
            return result.getBytes();
        }
    }
}
