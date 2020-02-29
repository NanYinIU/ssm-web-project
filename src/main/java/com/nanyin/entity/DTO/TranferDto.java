package com.nanyin.entity.DTO;

import lombok.Data;

/**
 * 穿梭框的DTO
 */
@Data
public class TranferDto {
    private String direction;
    private Integer[] value;
    private String standardKey;
    private Integer[] movedKeys;
}
