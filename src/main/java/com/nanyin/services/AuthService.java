package com.nanyin.services;

import com.nanyin.entity.Auth;

import java.util.List;

public interface AuthService {
    // test
    List<Auth> findNotDeletedAuth() throws Exception;
}
