package com.example.spring_security_with_jwt.repository;

import com.example.spring_security_with_jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
