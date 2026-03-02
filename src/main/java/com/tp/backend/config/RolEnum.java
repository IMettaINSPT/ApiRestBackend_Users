package com.tp.backend.config;

public enum RolEnum {
    ADMIN(1L),
    INVESTIGADOR(2L),
    VIGILANTE(3L);

    private final Long id;

    RolEnum(Long id) {
        this.id = id;
    }

    public Long getId() { return id; }

    // Si necesitas el prefijo en algún lado, lo generas dinámicamente
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}