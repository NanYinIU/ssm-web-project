package com.nanyin.services.impl;

import com.nanyin.entity.Organization;
import com.nanyin.entity.vo.OrganizationVo;
import com.nanyin.mapper.OrganizationVoMapper;
import com.nanyin.services.OrganizationVoService;
import org.apache.ibatis.annotations.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NanYin on 2017-07-18 下午2:33.
 * 包名： com.nanyin.services.impl
 * 类描述：
 */
@Service
public class OrganizationVoServiceImpl implements OrganizationVoService{

    @Autowired
    OrganizationVoMapper organizationVoMapper;


    public List<OrganizationVo> selectOrganizationVo() {
        return organizationVoMapper.selectOrganizationVo();
    }

    public Map<String,Object> displayOrganizationVo(Map<String,Object> map) {

//        需要 total 和 rows
        int total = organizationVoMapper.selectOrganizationVo().size();

        List<OrganizationVo> rows = organizationVoMapper.displayOrganizationVo(map) ;

        Map<String,Object> result = new HashMap<String, Object>();

        result.put("rows",rows);
        result.put("total",total);


        return result;
    }

    public int updateNode(OrganizationVo organizationVo) {
        return organizationVoMapper.updateNode(organizationVo);
    }

    public List orgEchart() {
//        查出类型

        List<OrganizationVo> orgList =  organizationVoMapper.selectOrganizationVo();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for(int i = 0 ; i < orgList.size() ; i ++){
            String orgName = orgList.get(i).getName();
            int count = organizationVoMapper.orgEchart(orgName);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("name",orgName);
            map.put("count",count);
            list.add(map);
        }
        return list;
    }


}
