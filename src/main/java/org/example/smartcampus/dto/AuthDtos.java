package org.example.smartcampus.dto;

import lombok.*;

public class AuthDtos {
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class RegisterRequest { private String email; private String password; }
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class LoginRequest { private String email; private String password; }
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class AuthResponse { private String token; }
}
