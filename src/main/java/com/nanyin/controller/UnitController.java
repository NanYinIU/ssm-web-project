package com.nanyin.controller;

import com.nanyin.config.util.Result;
import com.nanyin.entity.DTO.PageQueryParams;
import com.nanyin.entity.Unit;
import com.nanyin.services.UnitService;
import com.nanyin.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 单位管理 --- 结构：
 * 左侧：单位树，
 * 右侧上：单位基本信息，添加子单位，添加同级单位
 * 右侧下：单位下人员列表
 *
 */
@RestController
public class UnitController {

    @Autowired
    UnitService unitService;

    @Autowired
    UserServices userServices;

    /**
     * 获得所有单位列表（树）
     * @return
     */
    @GetMapping("/units")
    public Result findUnitTree() throws Exception{
        return new Result<>(unitService.findUnitTree());
    }

    @GetMapping("/unit/list")
    public Result findUnits(PageQueryParams pageQueryParams) throws Exception{
        return new Result<>(unitService.findUnits(pageQueryParams));
    }


    /**
     * 获得单位下人员列表
     * @param id
     * @param search
     * @param offset
     * @param limit
     * @param sort
     * @return
     * @throws Exception
     */
    @GetMapping("/unit/{id}/users")
    public Result findUnitUsers(@PathVariable Integer id,String search, Integer offset, Integer limit, String sort) throws Exception{
        return new Result<>(userServices.findUnitUsers(id,search,offset,limit,sort));
    }

    /**
     * 获得特定{id}的单位
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/unit/{id}")
    public Result findUnit(@PathVariable Integer id) throws Exception{
        return new Result();
    }

    /**
     * 添加单位
     * @param unit
     * @return
     * @throws Exception
     */
    @PostMapping("/unit")
    public Result addUnit(@RequestBody Unit unit) throws Exception{
        return new Result();
    }

    /**
     * 修改单位
     * @param unit
     * @return
     * @throws Exception
     */
    @PutMapping("/unit")
    public Result updateUnit(@RequestBody Unit unit) throws Exception{
        return new Result();
    }

}
