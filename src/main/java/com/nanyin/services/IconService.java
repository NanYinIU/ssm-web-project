package com.nanyin.services;

import com.nanyin.entity.icon.Icon;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: NanYin
 * @Date: 12/8/18 17:46
 * @Description:
 */
@Service
public interface IconService {

    List<Icon> findIconInfo(String iconName);

    Boolean addIcon(Icon icon);

    int getCountNumber();
}