package com.nanyin.services.impl;

import com.nanyin.entity.Operate;
import com.nanyin.repository.OperateRepository;
import com.nanyin.services.OperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperateServiceImpl implements OperateService {
    @Autowired
    OperateRepository operateRepository;

    @Override
    public Operate save(Operate operate){
       return operateRepository.save(operate);
    }
}
