package com.tp.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioUpdateRequest {

    @Size(min = 3, max = 50, message = "username debe tener entre 3 y 50 caracteres")
    public String username;

    @Size(min = 4, max = 120, message = "password debe tener entre 4 y 120 caracteres")
    public String password;

    @Size(min = 3, max = 30, message = "rol debe tener entre 3 y 30 caracteres")
    public String rol;
}
