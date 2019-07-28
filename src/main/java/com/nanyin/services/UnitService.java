package com.nanyin.services;

import com.nanyin.entity.Unit;

import java.util.List;

public interface UnitService {
    List<Unit> findNotDeletedUnit() throws Exception;
}

