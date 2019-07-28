package com.nanyin.services.impl;

import com.nanyin.entity.Auth;
import com.nanyin.enumEntity.DeletedStatusEnum;
import com.nanyin.repository.AuthRepository;
import com.nanyin.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthRepository authRepository;
    @Override
    @Cacheable("findNotDeletedAuth")
    public List<Auth> findNotDeletedAuth() throws Exception{
        return authRepository.findByIsDeletedOrderByOrd(DeletedStatusEnum.IS_NOT_DELETED.isJudge());
    }
}
