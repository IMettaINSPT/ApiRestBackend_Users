package com.tp.backend.dto.personaDetenida;

public class PersonaDetenidaResponse {
    private Long id;
    private String nombre;
    private String codigo;

    private Long bandaId;

    public PersonaDetenidaResponse(Long id,  String nombre, Long bandaId, String codigo) {
        this.id = id;
        this.nombre = nombre;
        this.bandaId = bandaId;
        this.codigo = codigo;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Long getBandaId() { return bandaId; }

    public String getCodigo() { return codigo; }

    public void setCodigo(String codigo) {this.codigo = codigo; }
}
