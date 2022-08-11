package com.example.spring_security_with_jwt.service.implementation;

import com.example.spring_security_with_jwt.entity.Role;
import com.example.spring_security_with_jwt.entity.User;
import com.example.spring_security_with_jwt.repository.RoleRepository;
import com.example.spring_security_with_jwt.repository.UserRepository;
//import com.example.spring_security_with_jwt.service.CustomUserDetailService;
import com.example.spring_security_with_jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null){
            log.error("user not found in the database");
            throw new UsernameNotFoundException("user not found in the database");
        }else{
            log.info("user found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority>authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getUserName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }
    @Override
    public User saveUser(User user) {
        log.info("saving new user{} to the database", user.getUserName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new role {} to the database", role.getUserName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        log.info("Adding role {} to user {}", roleName, userName);
        User user = userRepository.findByUserName(userName);
        Role role = roleRepository.findByUserName(roleName);
        user.getRoles().add(role);

    }

    @Override
    public User getUser(String userName) {
        log.info("Fetching user {}", userName);
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

}
