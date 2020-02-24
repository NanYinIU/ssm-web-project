package com.nanyin.config.util;

import com.google.common.base.Strings;
import com.nanyin.entity.User;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 简化JPA复杂查询（待完善）
 */
public class JpaHelper {

    private List<Predicate> predicatesList;
    private Root<User> root;
    private CriteriaQuery<?> criteriaQuery;
    private CriteriaBuilder criteriaBuilder;

    public JpaHelper(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        predicatesList = new ArrayList<>();
        this.root = root;
        this.criteriaQuery = criteriaQuery;
        this.criteriaBuilder = criteriaBuilder;
    }

    public JpaHelper add(Predicate p){
        predicatesList.add(p);
        return this;
    }

    public Predicate query(){
        Predicate[] predicates = new Predicate[predicatesList.size()];
        return criteriaBuilder.and(predicatesList.toArray(predicates));
    }

    public JpaHelper createOrder(String order){
        if(!Strings.isNullOrEmpty(order)){
            if(order.startsWith("+") || order.startsWith("-")){
                String[] split = order.trim().substring(1).split("\\.");
                if(split.length > 0){
                    Path<Object> objectPath = null;
                    for (int i = 0; i < split.length; i++) {
                        if(i == 0){
                            objectPath = root.get(split[0]);
                        }else{
                            objectPath = objectPath.get(split[i]);
                        }
                    }
                    if(order.startsWith("-")){
                        criteriaQuery.orderBy(criteriaBuilder.desc(objectPath));
                    }else if(order.startsWith("+")){
                        criteriaQuery.orderBy(criteriaBuilder.asc(objectPath));
                    }
                }
            }
        }
        return this;
    }




}
