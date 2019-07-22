package com.nanyin.services;

import com.nanyin.entity.wrapper.StandardWrapper;

import java.util.List;

public interface ProjectServices {
     List<StandardWrapper> getStandardProjectStatus();

     List<StandardWrapper> getStandardProjectLevel();

     List<StandardWrapper> getStandardProjectType();


}
