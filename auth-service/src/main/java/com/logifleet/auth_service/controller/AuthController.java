package com.logifleet.auth_service.controller;

import com.logifleet.auth_service.entity.User;
import com.logifleet.auth_service.repository.UserRepository;
import com.logifleet.auth_service.security.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public String login(@RequestBody User loginRequest) {

        User user = userRepository
                .findByUsername(loginRequest.getUsername())
                .orElse(null);

        if (user == null) {
            return "Invalid username or password";
        }

        if (!passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword())) {

            return "Invalid username or password";
        }

        return jwtService.generateToken(
                user.getUsername(),
                user.getRole()
        );
    }
}