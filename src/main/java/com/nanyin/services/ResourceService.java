package com.nanyin.services;

import com.nanyin.entity.Resource;

import java.util.List;

public interface ResourceService {
    List<Resource> getSidebarInfoWapper(String username) throws Exception;
}
