package com.tp.backend.usuario.dto.login;

public record AuthResponse(
        String token,
        String username,
        String rol
) {}