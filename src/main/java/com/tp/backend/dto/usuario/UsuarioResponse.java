package com.tp.backend.dto.usuario;

import com.tp.backend.config.RolEnum;

public class UsuarioResponse {

    private Long id;
    private String username;
    private RolEnum rol;      // ADMIN | INVESTIGADOR | VIGILANTE
    private boolean enabled;

    public UsuarioResponse(Long id, String username, RolEnum rol, boolean enabled) {
        this.id = id;
        this.username = username;
        this.rol = rol;
        this.enabled = enabled;
    }

    // ===== getters =====

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public RolEnum getRol() {
        return rol;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
