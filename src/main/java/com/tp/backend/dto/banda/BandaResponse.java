package com.tp.backend.dto.banda;

public class BandaResponse {
    private Long id;
    private String nombre;

    public BandaResponse(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
}
