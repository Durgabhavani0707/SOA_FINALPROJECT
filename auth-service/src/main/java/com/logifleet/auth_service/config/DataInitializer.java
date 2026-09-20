package com.logifleet.auth_service.config;

import com.logifleet.auth_service.entity.User;
import com.logifleet.auth_service.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createAdminUser(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (userRepository.findByUsername("admin").isEmpty()) {

                User user = new User();

                user.setUsername("admin");
                user.setPassword(
                        passwordEncoder.encode("admin123")
                );
                user.setRole("ADMIN");

                userRepository.save(user);

                System.out.println("Admin user created successfully");
            }
        };
    }
}