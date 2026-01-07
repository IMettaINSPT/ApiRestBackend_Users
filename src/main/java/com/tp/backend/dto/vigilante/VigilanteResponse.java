package com.tp.backend.dto.vigilante;

public class VigilanteResponse {

    private Long id;
    private String codigo;
    private int edad;

    public VigilanteResponse(Long id, String codigo, int edad) {
        this.id = id;
        this.codigo = codigo;
        this.edad = edad;
    }

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public int getEdad() { return edad; }
}
