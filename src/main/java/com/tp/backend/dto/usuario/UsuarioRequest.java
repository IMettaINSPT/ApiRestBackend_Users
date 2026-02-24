package com.tp.backend.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsuarioRequest {

    @NotBlank(message = "El código de usuario es obligatorio")
    @Size(min = 3, max = 20, message = "El código debe tener entre 3 y 20 caracteres")
    private String codigo; // ✅ Agregado para consistencia con el sistema

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
    private String username;

    @NotBlank(message = "La password es obligatoria")
    @Size(min = 4, max = 100, message = "La password debe tener al menos 4 caracteres")
    private String password;

    @NotBlank(message = "El rol es obligatorio") // ✅ Cambiado de 'tipo' a 'rol' para matchear con el Front
    @Pattern(
            regexp = "ADMIN|INVESTIGADOR|VIGILANTE|USER", // ✅ Agregamos USER si lo vas a usar
            message = "Rol inválido. Valores permitidos: ADMIN, INVESTIGADOR, VIGILANTE, USER"
    )
    private String rol;

    private Long vigilanteId;

    // --- Getters y Setters ---

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Long getVigilanteId() { return vigilanteId; }
    public void setVigilanteId(Long vigilanteId) { this.vigilanteId = vigilanteId; }
}