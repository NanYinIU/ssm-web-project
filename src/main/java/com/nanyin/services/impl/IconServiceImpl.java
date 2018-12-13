package com.nanyin.services.impl;

import com.nanyin.entity.icon.Icon;
import com.nanyin.mapper.IconMapper;
import com.nanyin.services.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: NanYin
 * @Date: 12/8/18 17:47
 * @Description:
 */
@Service
public class IconServiceImpl implements IconService {
    @Autowired
    IconMapper iconMapper;

    public List<Icon> findIconInfo(String name,Integer page,Integer limit) {
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("iconName",name);
        param.put("limit",limit);
        param.put("start",(page-1)*limit);
        return iconMapper.findIconIf(param);
    }

    public Integer countIconIf(String iconName) {
        return iconMapper.countIconIf(iconName);
    }

    public Boolean addIcon(Icon icon) {
        return iconMapper.addIcon(icon)==1;
    }

    public Boolean modifyIcon(Icon icon) {
        return iconMapper.updateIcon(icon)==1;
    }

    public int getCountNumber() {
        return iconMapper.getCountNumber();
    }

    public Boolean deleteIcon(Integer id) {
        return iconMapper.deleteIcon(id)==1;
    }

    public boolean deleteIcons(String ids) {
        String[] arrays = ids.split(",");
        List<Integer> idList = new LinkedList<Integer>();
        for (String s:arrays
        ) {
            Integer id = Integer.parseInt(s);
            idList.add(id);
        }
        return iconMapper.deleteIcons(idList)==1;
    }
}