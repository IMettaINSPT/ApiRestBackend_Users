package com.tp.backend.dto;

import java.time.Instant;

public class LoginResponse {
    public String tokenType = "Bearer";
    public String accessToken;
    public Instant expiresAt;
    public String username;
    public String rol;

    public LoginResponse(String accessToken, Instant expiresAt, String username, String rol) {
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
        this.username = username;
        this.rol = rol;
    }
}
