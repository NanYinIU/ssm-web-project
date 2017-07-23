package com.nanyin.mapper;

import com.nanyin.entity.vo.OrganizationVo;

import java.util.List;
import java.util.Map;

/**
 * Created by NanYin on 2017-07-18 下午2:26.
 * 包名： com.nanyin.mapper
 * 类描述：
 */
public interface OrganizationVoMapper {

    public List<OrganizationVo> selectOrganizationVo();

    public List<OrganizationVo> displayOrganizationVo(Map<String,Object> map);

    public int getPId(int id);

    public int updateNode(OrganizationVo organizationVo);
}
