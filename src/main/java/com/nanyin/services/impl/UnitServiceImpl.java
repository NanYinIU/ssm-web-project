package com.nanyin.services.impl;

import com.nanyin.entity.Organization;
import com.nanyin.entity.vo.OrganizationVo;
import com.nanyin.mapper.UnitMapper;
import com.nanyin.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by NanYin on 2017-07-11 上午10:03.
 * 包名： com.nanyin.services.impl
 * 类描述：
 */
@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitMapper unitMapper;

    public List<Organization> selectOrganizations() {
        return unitMapper.selectOrganizations();
    }

    public String selectOrganizationById(int id) {
        return unitMapper.selectOrganizationById(id);
    }

    public int updateById(Organization organization) {
        return unitMapper.updateById(organization);
    }

    public int delectOrgById(int id) {
        return unitMapper.delectOrgById(id);
    }

    public int insertOrg(OrganizationVo organization) {
        unitMapper.insertOrg(organization);

        String name = organization.getName();
//      返回插入的id值
        return unitMapper.selectIdByName(name);
    }


}
