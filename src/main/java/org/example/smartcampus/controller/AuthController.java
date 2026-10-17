package org.example.smartcampus.controller;

import org.example.smartcampus.dto.AuthDtos;
import org.example.smartcampus.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthDtos.RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDtos.AuthResponse> login(@RequestBody AuthDtos.LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }
}
