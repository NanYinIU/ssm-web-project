package com.nanyin.services.impl;

import com.nanyin.entity.Unit;
import com.nanyin.repository.UnitRepository;
import com.nanyin.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    UnitRepository unitRepository;

    @Override
    public List<Unit> findUnits() throws Exception {
        return unitRepository.findAll();
    }

}
