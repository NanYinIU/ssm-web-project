package com.nanyin.services.impl;

import com.nanyin.entity.Organization;
import com.nanyin.mapper.OrganizationMapper;
import com.nanyin.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by NanYin on 2017-07-11 上午10:03.
 * 包名： com.nanyin.services.impl
 * 类描述：
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    private OrganizationMapper organizationMapper;
    public List<Organization> selectOrganizations() {
        return organizationMapper.selectOrganizations();
    }

    public String selectOrganizationById(int id) {
        return organizationMapper.selectOrganizationById(id);
    }

    public int updateById(Organization organization) {
        return organizationMapper.updateById(organization);
    }

    public int delectOrgById(int id) {
        return organizationMapper.delectOrgById(id);
    }


}
