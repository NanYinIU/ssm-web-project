package com.nanyin.entity.dto;

import com.nanyin.config.operateLog.OperateModul;
import com.nanyin.config.operateLog.OperationType;
import lombok.Data;

@Data
public class OperateLogDto {
    private OperationType operationType;
    private String operationName;
    private OperateModul operateModul;
}
