package com.nanyin.services;

import com.nanyin.entity.Unit;
import com.nanyin.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UnitService {

    /**
     * 获得所有unit数据
     * @return
     * @throws Exception
     */
    List<Unit> findUnits() throws Exception;


}
