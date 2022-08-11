package com.example.spring_security_with_jwt.repository;

import com.example.spring_security_with_jwt.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>

    {
        Role findByUserName(String userName);
    }

