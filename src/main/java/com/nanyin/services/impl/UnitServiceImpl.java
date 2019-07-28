package com.nanyin.services.impl;

import com.nanyin.entity.Unit;
import com.nanyin.enumEntity.DeletedStatusEnum;
import com.nanyin.repository.UnitRepository;
import com.nanyin.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    UnitRepository unitRepository;

    @Cacheable("findNotDeletedUnit")
    @Override
    public List<Unit> findNotDeletedUnit() {
        return unitRepository.findAllByIsDeletedOrderByOrd(DeletedStatusEnum.IS_NOT_DELETED.isJudge());
    }
}
