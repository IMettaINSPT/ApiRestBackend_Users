package com.tp.backend.config;

public enum RolEnum {
    ADMIN(1L, "ROLE_ADMIN"),
    INVESTIGADOR(2L, "ROLE_INVESTIGADOR"),
    VIGILANTE(3L, "ROLE_VIGILANTE");

    private final Long id;
    private final String authority;

    RolEnum(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public Long getId() { return id; }
    public String getAuthority() { return authority; }
}