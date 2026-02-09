package com.tp.backend.dto.usuario;

import com.tp.backend.config.RolEnum;

public class UsuarioResponse {

    private Long id;
    private String username;
    private RolEnum rol;      // ADMIN | INVESTIGADOR | VIGILANTE
    private boolean enabled;
    private Long vigilanteId;

    public UsuarioResponse(Long id, String username, RolEnum rol, boolean enabled, Long vigilanteId) {
        this.id = id;
        this.username = username;
        this.rol = rol;
        this.enabled = enabled;
        this.vigilanteId = vigilanteId;
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

    public Long getVigilanteId() {
        return vigilanteId;
    }

    public void setVigilanteId(Long vigilanteId) {
        this.vigilanteId = vigilanteId;
    }
}
