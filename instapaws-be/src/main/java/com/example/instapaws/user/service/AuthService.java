package com.example.instapaws.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.instapaws.model.AuthResult;
import com.example.instapaws.model.User;
import com.example.instapaws.repository.UserRepository;
import com.example.instapaws.utils.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResult registerUser(String username, String password) {

        if (userRepository.findByUsername(username).isPresent()) {
            return new AuthResult(false, "Username already taken", null);
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);

        userRepository.save(user);
        return new AuthResult(true, "User registered successfully", user);
    }

    public AuthResult login(String username, String rawPassword) {

        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return new AuthResult(false, "Invalid username or password", null);
        }

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            return new AuthResult(false, "Invalid username or password", null);
        }

        return new AuthResult(true, "Login success", user);
    }
}


