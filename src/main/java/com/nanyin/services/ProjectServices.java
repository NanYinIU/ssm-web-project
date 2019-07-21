package com.nanyin.services;

import com.nanyin.entity.wrapper.Standard;

import java.util.List;
import java.util.Map;

public interface ProjectServices {
     List<Standard> getStandardProjectStatus();

     List<Standard> getStandardProjectLevel();

     List<Standard> getStandardProjectType();


}
