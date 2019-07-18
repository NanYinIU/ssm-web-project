package com.nanyin.services;

import com.nanyin.entity.icon.Icon;
import com.nanyin.entity.icon.vo.IconCombo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: NanYin
 * @Date: 12/8/18 17:46
 * @Description:
 */
@Service
public interface IconService {

    List<Icon> findIconInfo(String name,Integer page,Integer limit);

    Integer countIconIf(String name);

    Boolean addIcon(Icon icon);

    Boolean modifyIcon(Icon icon);

    int getCountNumber();

    Boolean deleteIcon(Integer id);

    boolean deleteIcons(String ids);

    Map<String,Object> findIconCombo();
}