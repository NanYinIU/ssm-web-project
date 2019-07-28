package com.nanyin.enumEntity;

public enum SystemStatusEnum {
    /*
     * 正常状态 为 1
     * normal status is 1
     **/
    NORMAL(1),
    /*
     * 暂时被禁止登陆 为2
     **/
    BLOCK_FOR_TEMP(2),
    /*
     * 永久被禁为3
     **/
    BLOCK_FORVER(3);

    private Integer id;

    SystemStatusEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
