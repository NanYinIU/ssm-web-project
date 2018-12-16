package com.nanyin.mapper;

import com.nanyin.entity.icon.Icon;
import com.nanyin.entity.icon.vo.IconCombo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface IconMapper {

    List<Icon> findIconIf(Map param);

    Integer addIcon(Icon icon);

    Integer countIconIf(String name);

    Integer updateIcon(Icon icon);

    int getCountNumber();

    Integer deleteIcons(List<Integer> id);

    Integer deleteIcon(Integer id);

    List<IconCombo> findIconCombo();

}


