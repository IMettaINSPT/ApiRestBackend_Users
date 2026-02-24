package com.tp.backend.dto.usuario;

import com.tp.backend.config.RolEnum;

public class UsuarioResponse {

    private Long id;
    private String codigo;          // ✅ Agregado: El identificador de negocio
    private String username;
    private RolEnum rol;            // ADMIN | INVESTIGADOR | VIGILANTE
    private boolean enabled;
    private Long vigilanteId;
    private String vigilanteCodigo; // ✅ Agregado: Para no mostrar solo un ID numérico en la tabla

    // Constructor actualizado
    public UsuarioResponse(Long id, String codigo, String username, RolEnum rol, boolean enabled, Long vigilanteId, String vigilanteCodigo) {
        this.id = id;
        this.codigo = codigo;
        this.username = username;
        this.rol = rol;
        this.enabled = enabled;
        this.vigilanteId = vigilanteId;
        this.vigilanteCodigo = vigilanteCodigo;
    }

    // ===== Getters y Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public RolEnum getRol() { return rol; }
    public void setRol(RolEnum rol) { this.rol = rol; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public Long getVigilanteId() { return vigilanteId; }
    public void setVigilanteId(Long vigilanteId) { this.vigilanteId = vigilanteId; }

    public String getVigilanteCodigo() { return vigilanteCodigo; }
    public void setVigilanteCodigo(String vigilanteCodigo) { this.vigilanteCodigo = vigilanteCodigo; }
}