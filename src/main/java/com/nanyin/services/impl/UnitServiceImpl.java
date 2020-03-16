package com.nanyin.services.impl;

import com.nanyin.config.util.PageHelper;
import com.nanyin.entity.DTO.PageQueryParams;
import com.nanyin.entity.DTO.UnitDto;
import com.nanyin.entity.QUnit;
import com.nanyin.entity.Unit;
import com.nanyin.repository.UnitRepository;
import com.nanyin.services.UnitService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Unit> findUnits(PageQueryParams pageQueryParams) throws Exception {
        Integer offset = pageQueryParams.getOffset();
        Integer limit = pageQueryParams.getLimit();
        PageRequest pageRequest = PageHelper.generatePageRequest(offset, limit);
        String search = pageQueryParams.getSearch();
        Integer unitId = pageQueryParams.getUnit();

        QUnit unit = QUnit.unit;
        Predicate predicate = unit.isNotNull().or(unit.isNull());
        predicate = search == null ? predicate : ExpressionUtils.and(predicate, unit.name.like("%" + search + "%"));
        predicate = unitId == null ?
                ExpressionUtils.and(predicate, unit.parent.id.isNull()) :
                ExpressionUtils.and(predicate, unit.parent.id.eq(unitId).and(unit.id.ne(unitId)));
        return unitRepository.findAll(predicate, pageRequest);
    }

    @Override
    public List<Unit> findUnitTree() throws Exception {
        return unitRepository.findAllByParentIsNull();
    }

}
