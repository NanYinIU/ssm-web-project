package com.nanyin.services;

import com.nanyin.entity.NavBar;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: NanYin
 * @Date: 11/20/18 20:58
 * @Description:
 */
@Service
public interface NavBarService {
    List<NavBar> findNavBarByUserId(Integer userId);
}