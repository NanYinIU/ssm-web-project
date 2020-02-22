package com.nanyin.repository;

import com.nanyin.entity.Sex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SexRepository extends JpaRepository<Sex,Integer> {
}
