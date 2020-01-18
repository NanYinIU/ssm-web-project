package com.nanyin.entity.DTO;

import com.nanyin.config.enums.OperateModuleEnum;
import com.nanyin.config.enums.OperationTypeEnum;
import lombok.Data;

@Data
public class OperateLogDto {
    private OperationTypeEnum operationTypeEnum;
    private String operationName;
    private OperateModuleEnum operateModuleEnum;
}
