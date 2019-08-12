package com.nanyin.services.impl;

import com.nanyin.entity.SocialType;
import com.nanyin.repository.SocialTypeRepository;
import com.nanyin.services.SocialMediaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialMediaServiceImpl implements SocialMediaServices {
    @Autowired
    SocialTypeRepository socialTypeRepository;

    @Override
    public List<SocialType> getSupportSocialType() {
        return socialTypeRepository.findAll();
    }
}
