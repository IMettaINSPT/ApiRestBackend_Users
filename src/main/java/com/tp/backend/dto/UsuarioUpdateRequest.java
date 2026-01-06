package com.tp.backend.dto;

import jakarta.validation.constraints.Size;

public class UsuarioUpdateRequest {

    @Size(min = 4, max = 100, message = "La password debe tener al menos 4 caracteres")
    private String password; // opcional

    private Boolean enabled; // opcional

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
}
