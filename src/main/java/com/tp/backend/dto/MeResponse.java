package com.tp.backend.dto;

public class MeResponse {
    public String username;
    public String rol;

    public MeResponse(String username, String rol) {
        this.username = username;
        this.rol = rol;
    }
}
