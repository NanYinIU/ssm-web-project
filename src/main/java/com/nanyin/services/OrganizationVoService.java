package com.nanyin.services;

import com.nanyin.entity.vo.OrganizationVo;

import java.util.List;
import java.util.Map;

/**
 * Created by NanYin on 2017-07-18 下午2:32.
 * 包名： com.nanyin.services
 * 类描述：
 */
public interface OrganizationVoService {
    List<OrganizationVo> selectOrganizationVo();

    Map<String,Object> displayOrganizationVo(Map<String,Object> map);

    int updateNode(OrganizationVo organizationVo);
}
