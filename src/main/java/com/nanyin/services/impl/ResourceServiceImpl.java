package com.nanyin.services.impl;

import com.nanyin.entity.Resource;
import com.nanyin.repository.ResourceRepository;
import com.nanyin.services.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    ResourceRepository resourceRepository;


    @Override
    public List<Resource> getSidebarInfoWapper(String username) throws Exception {
        return resourceRepository.findByAuths_Users_NameAndType_IdOrderByOrdAsc(username,1);
    }
}