package com.nanyin.entity.dto;

import com.nanyin.config.annotation.OperateModul;
import com.nanyin.config.annotation.OperationType;
import lombok.Data;

@Data
public class OperateLogDto {
    private OperationType operationType;
    private String operationName;
    private OperateModul operateModul;
}
