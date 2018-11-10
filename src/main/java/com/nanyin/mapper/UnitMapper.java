package com.nanyin.mapper;

import com.nanyin.entity.Organization;
import com.nanyin.entity.vo.OrganizationVo;

import java.util.List;

/**
 * Created by NanYin on 2017-07-11 上午9:46.
 * 包名： com.nanyin.mapper
 * 类描述：组织 也就是部门的mapper类
 */
public interface UnitMapper {
    List<Organization> selectOrganizations();

    String selectOrganizationById(int id);

    int selectOrganizationByName(String name);

    int updateById(Organization organization);

    int delectOrgById(int id);

    int insertOrg(OrganizationVo organization);

    int selectIdByName(String name);
}
