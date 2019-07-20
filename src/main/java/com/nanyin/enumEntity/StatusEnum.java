package com.nanyin.enumEntity;

import lombok.Data;

public enum StatusEnum {

    NORMAL(1),BLOCK_FOR_TEMP(2),BLOCK_FORVER(3);

    private Integer id;

    StatusEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
