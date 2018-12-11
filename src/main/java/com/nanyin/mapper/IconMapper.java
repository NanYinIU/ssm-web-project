package com.nanyin.mapper;

import com.nanyin.entity.icon.Icon;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IconMapper {

    List<Icon> findIconIf(String iconName);

    Integer addIcon(Icon icon);

    Integer updateIcon(Icon icon);

    int getCountNumber();

    Integer deleteIcons(List<Integer> id);

    Integer deleteIcon(Integer id);

}


