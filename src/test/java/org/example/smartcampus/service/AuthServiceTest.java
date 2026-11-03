package org.example.smartcampus.service;

import org.example.smartcampus.dto.AuthDtos;
import org.example.smartcampus.entity.User;
import org.example.smartcampus.repository.UserRepository;
import org.example.smartcampus.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    @Mock
    private UserRepository users;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private JwtUtil jwt;
    @InjectMocks
    private AuthService authService;

    public AuthServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_savesNewUser() {
        AuthDtos.RegisterRequest req = new AuthDtos.RegisterRequest("test@campus.ma", "pass123");
        when(users.existsByEmail("test@campus.ma")).thenReturn(false);
        when(encoder.encode("pass123")).thenReturn("encoded");
        authService.register(req);
        verify(users, times(1)).save(any(User.class));
    }

    @Test
    void login_returnsToken_whenValid() {
        User u = User.builder().email("test@campus.ma").password("encoded").role("STUDENT").build();
        AuthDtos.LoginRequest req = new AuthDtos.LoginRequest("test@campus.ma", "pass123");
        when(users.findByEmail("test@campus.ma")).thenReturn(Optional.of(u));
        when(encoder.matches("pass123", "encoded")).thenReturn(true);
        when(jwt.generateToken("test@campus.ma")).thenReturn("jwt-token");
        AuthDtos.AuthResponse res = authService.login(req);
        assertEquals("jwt-token", res.getToken());
    }
}
