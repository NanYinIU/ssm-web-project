package com.nanyin.services;

import com.nanyin.entity.DTO.PageQueryParams;
import com.nanyin.entity.Unit;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UnitService {

    /**
     * 获得所有unit数据
     * @return
     * @throws Exception
     * @param pageQueryParams
     */
    Page<Unit> findUnits(PageQueryParams pageQueryParams) throws Exception;

    /**
     * 查找单位树数据
     * @return
     * @throws Exception
     */
    List<Unit> findUnitTree() throws Exception;

}
