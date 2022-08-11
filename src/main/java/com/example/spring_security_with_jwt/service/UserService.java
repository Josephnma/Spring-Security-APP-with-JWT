package com.example.spring_security_with_jwt.service;

import com.example.spring_security_with_jwt.entity.Role;
import com.example.spring_security_with_jwt.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String userName, String roleName);
    User getUser(String userName);
    List<User> getUsers();
}
