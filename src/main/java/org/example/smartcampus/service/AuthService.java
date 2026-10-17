package org.example.smartcampus.service;

import org.example.smartcampus.dto.AuthDtos;
import org.example.smartcampus.entity.User;
import org.example.smartcampus.repository.UserRepository;
import org.example.smartcampus.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;
    public AuthService(UserRepository users, PasswordEncoder encoder, JwtUtil jwt) {
        this.users = users; this.encoder = encoder; this.jwt = jwt;
    }

    public void register(AuthDtos.RegisterRequest req) {
        if (users.existsByEmail(req.getEmail())) throw new RuntimeException("Email already used");
        users.save(User.builder().email(req.getEmail()).password(encoder.encode(req.getPassword())).role("STUDENT").build());
    }

    public AuthDtos.AuthResponse login(AuthDtos.LoginRequest req) {
        User u = users.findByEmail(req.getEmail()).orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!encoder.matches(req.getPassword(), u.getPassword())) throw new RuntimeException("Invalid credentials");
        return new AuthDtos.AuthResponse(jwt.generateToken(u.getEmail()));
    }
}
