package com.nanyin.enumEntity;

public enum DeletedStatusEnum {

    IS_NOT_DELETED(false),

    IS_DELETED(true);

    private DeletedStatusEnum(boolean judge) {
        this.judge = judge;
    }

    private boolean judge;

    public boolean isJudge() {
        return judge;
    }
}
