package com.tp.backend.dto.login;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "username es obligatorio")
    public String username;

    @NotBlank(message = "password es obligatorio")
    public String password;
}
