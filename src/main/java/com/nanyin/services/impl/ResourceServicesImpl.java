package com.nanyin.services.impl;

import com.nanyin.config.util.SessionUtil;
import com.nanyin.entity.wrapper.NavbarInfoWrapper;
import com.nanyin.services.ResourceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServicesImpl implements ResourceServices {

//    @Autowired
//    ResourceMapper resourceMapper;

    @Override
    public List<NavbarInfoWrapper> getSidebarInfoWapper() {
        String username = (String) SessionUtil.getAttribute("username");
        return null;
    }
}
