package com.tp.backend.dto;

public class UsuarioResponse {
    public Long id;
    public String username;
    public String rol;

    public UsuarioResponse(Long id, String username, String rol) {
        this.id = id;
        this.username = username;
        this.rol = rol;
    }
}
