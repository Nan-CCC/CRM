package com.example.enterprisecrm.service;

import com.example.enterprisecrm.entity.User;

public interface UserService {
    public User login(String id, String password);
}
