package com.tp.backend.dto.usuario;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsuarioUpdateRequest {

    @Size(min = 4, max = 100, message = "La password debe tener al menos 4 caracteres")
    private String password; // opcional

    private Boolean enabled; // opcional

    @Pattern(
            regexp = "ADMIN|INVESTIGADOR|VIGILANTE|USER",
            message = "Rol inválido. Valores permitidos: ADMIN, INVESTIGADOR, VIGILANTE, USER"
    )
    private String rol; // ✅ Agregado: para poder cambiar el rango del usuario

    private Long vigilanteId; // ✅ Agregado: para poder reasignar o quitar el vigilante

    // ===== getters / setters =====

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getVigilanteId() {
        return vigilanteId;
    }

    public void setVigilanteId(Long vigilanteId) {
        this.vigilanteId = vigilanteId;
    }
}