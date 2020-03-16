package com.nanyin.controller;

import com.nanyin.config.util.Result;
import com.nanyin.entity.DTO.PageQueryParams;
import com.nanyin.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @GetMapping("/permissions")
    public Result permissions(PageQueryParams pageQueryParams) throws Exception{
        return new Result<>(permissionService.findPermissions(pageQueryParams));
    }
}
