package com.nanyin.entity.DTO;

import lombok.Data;

@Data
public class PageQueryParams {
    private String search;
    private Integer offset;
    private Integer limit;
    private String sort;
    /**
     * 角色ID
     */
    private Integer role;

    /**
     * 单位id
     */
    private Integer unit;
}
