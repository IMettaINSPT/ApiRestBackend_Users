package com.tp.backend.dto;

public class MeResponse {
    public String username;
    public String rol;
    public Long rolId;

    public MeResponse(String username, String rol, Long rolId) {
        this.username = username;
        this.rol = rol;
        this.rolId = rolId;
    }
}
