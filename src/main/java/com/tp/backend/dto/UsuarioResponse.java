package com.tp.backend.dto;

public class UsuarioResponse {

    private Long id;
    private String username;
    private String rol;      // ADMIN | INVESTIGADOR | VIGILANTE
    private boolean enabled;

    public UsuarioResponse(Long id, String username, String rol, boolean enabled) {
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

    public String getRol() {
        return rol;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
