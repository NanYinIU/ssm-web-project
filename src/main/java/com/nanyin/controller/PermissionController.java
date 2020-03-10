package com.nanyin.controller;

import com.nanyin.config.util.Result;
import com.nanyin.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @GetMapping("/permissions")
    public Result permissions(String search, Integer offset, Integer limit, String sort) throws Exception{
        return new Result<>(permissionService.findPermissions(search, offset, limit, sort));
    }
}
