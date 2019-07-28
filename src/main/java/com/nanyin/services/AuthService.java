package com.nanyin.services;

import com.nanyin.entity.Auth;

import java.util.List;

public interface AuthService {
    List<Auth> findNotDeletedAuth();
}
