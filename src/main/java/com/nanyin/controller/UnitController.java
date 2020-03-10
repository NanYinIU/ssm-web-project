package com.nanyin.controller;

import com.nanyin.config.util.Result;
import com.nanyin.entity.Unit;
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

    /**
     * 获得所有单位列表（树）
     * @return
     */
    @GetMapping("/units")
    public Result findUnits() throws Exception{
        return new Result();
    }


    @GetMapping("/unit/{id}/users")
    public Result findUnitUser(@PathVariable Integer id,String search, Integer offset, Integer limit, String sort) throws Exception{
        return new Result();
    }

    @GetMapping("/unit/{id}")
    public Result findUnit(@PathVariable Integer id) throws Exception{
        return new Result();
    }
    @PostMapping("/unit")
    public Result addUnit(@RequestBody Unit unit) throws Exception{
        return new Result();
    }

    @PutMapping("/unit")
    public Result updateUnit(@RequestBody Unit unit) throws Exception{
        return new Result();
    }

}
