package com.tp.backend.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsuarioRequest {

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
    private String username;

    @NotBlank(message = "La password es obligatoria")
    @Size(min = 4, max = 100, message = "La password debe tener al menos 4 caracteres")
    private String password;

    @NotBlank(message = "El tipo de usuario es obligatorio")
    @Pattern(
            regexp = "ADMIN|INVESTIGADOR|VIGILANTE",
            message = "Tipo inválido. Valores permitidos: ADMIN, INVESTIGADOR, VIGILANTE"
    )
    private String tipo;

    // ===== getters / setters =====

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
