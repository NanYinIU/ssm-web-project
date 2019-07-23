package com.nanyin.repository;

import com.nanyin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByName(String name);
}
