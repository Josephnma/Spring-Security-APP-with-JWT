package com.example.spring_security_with_jwt;

import com.example.spring_security_with_jwt.entity.Role;
import com.example.spring_security_with_jwt.entity.User;
import com.example.spring_security_with_jwt.repository.UserRepository;
import com.example.spring_security_with_jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringSecurityWithJwtApplication {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initUsers(){
        List<User> users = Stream.of(
                new User(101,"joseph", "password", "joseph@yahoo.com"),
                new User(102,"user1", "pwd1", "user1@yahoo.com"),
                new User(103,"user2", "pwd2", "user2@yahoo.com"),
                new User(104,"user3", "pwd3", "user3@yahoo.com")

        ).collect(Collectors.toList());
       userRepository.saveAll(users);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityWithJwtApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService){
        return args -> {
            userService.saveRole(new Role(1, "ROLE_USER"));
            userService.saveRole(new Role(2, "ROLE_MANAGER"));
            userService.saveRole(new Role(3, "ROLE_ADMIN"));
            userService.saveRole(new Role(4, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new User(5, "John", "1534", "josgolf5@gmail.com", new ArrayList<>()));
            userService.saveUser(new User(6, "Jonah", "1434", "josgolf6@gmail.com", new ArrayList<>()));
            userService.saveUser(new User(7, "JoJo", "1634", "josgolf7@gmail.com", new ArrayList<>()));
            userService.saveUser(new User(8, "Joell", "1734", "josgolf8@gmail.com", new ArrayList<>()));

            userService.addRoleToUser("John", "ROLE_USER");
            userService.addRoleToUser("Jonah", "ROLE_MANAGER");
            userService.addRoleToUser("JoJo", "ROLE_ADMIN");
            userService.addRoleToUser("Joell", "ROLE_USER");
            userService.addRoleToUser("John", "ROLE_MANAGER");
        };
    }


}
