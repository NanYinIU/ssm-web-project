package com.nanyin.services;

import com.nanyin.entity.Resource;

import java.util.List;

public interface ResourceServices  {
    List<Resource> getSidebarInfoWapper(String username) throws Exception;
}
