package com.nanyin.services.impl;

import com.nanyin.config.util.SessionUtil;
import com.nanyin.entity.Resource;
import com.nanyin.repository.ResourceRepository;
import com.nanyin.services.ResourceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ResourceServicesImpl implements ResourceServices {

    @Autowired
    ResourceRepository resourceRepository;

//    @Cacheable("getSidebarInfoWapper")
    @Override
    public List<Resource> getSidebarInfoWapper() {
        String username = (String) SessionUtil.getAttribute("username");
        return resourceRepository.findByAuths_Users_NameAndType_IdOrderByOrdAsc(username,1);
    }
}
