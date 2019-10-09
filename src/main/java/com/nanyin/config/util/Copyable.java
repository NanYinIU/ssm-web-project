package com.nanyin.config.util;

import com.nanyin.entity.DTO.UserInfoDto;
import com.nanyin.entity.Sex;
import com.nanyin.entity.User;

import java.lang.reflect.InvocationTargetException;

/**
 * 根据类型进行判断相互转化的接口，需要各自的Dto 、DO进行implements
 * @param <D>
 * @param <T>
 */
public abstract class Copyable<D,T> {
    /**
     * 由 T 转化为 D
     * @param t
     * @return
     */
    protected abstract D transferFrom(T t) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    /**
     * 有 D 转化为 T
     * @param d
     * @return
     */
    protected abstract T reverseTransfer(D d);

}
