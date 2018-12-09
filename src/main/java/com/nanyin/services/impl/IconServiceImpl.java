package com.nanyin.services.impl;

import com.nanyin.entity.icon.Icon;
import com.nanyin.mapper.IconMapper;
import com.nanyin.services.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: NanYin
 * @Date: 12/8/18 17:47
 * @Description:
 */
@Service
public class IconServiceImpl implements IconService {
    @Autowired
    IconMapper iconMapper;

    public List<Icon> findIconInfo(String iconName) {
        return iconMapper.findIconIf(iconName);
    }

    public Boolean addIcon(Icon icon) {
        return iconMapper.addIcon(icon)==1;
    }

    public int getCountNumber() {
        return iconMapper.getCountNumber();
    }
}