package com.nanyin.services;

import com.nanyin.entity.Organization;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by NanYin on 2017-07-11 上午10:02.
 * 包名： com.nanyin.services
 * 类描述：
 */
@Service
public interface OrganizationService {
    List<Organization> selectOrganizations();
    String selectOrganizationById(int id);
    int updateById(Organization organization);
    int delectOrgById(int id);
}
