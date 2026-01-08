package com.tp.backend.dto.personaDetenida;

public class PersonaDetenidaResponse {
    private Long id;
    private String nombre;
    private String codigo;

    private Long bandaId;
    private String bandaNombre;

    public PersonaDetenidaResponse(Long id,  String nombre, Long bandaId, String bandaNombre, String codigo) {
        this.id = id;
        this.nombre = nombre;
        this.bandaId = bandaId;
        this.bandaNombre = bandaNombre;
        this.codigo = codigo;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Long getBandaId() { return bandaId; }
    public String getBandaNombre() { return bandaNombre; }

    public String getCodigo() { return codigo; }

    public void setCodigo(String codigo) {this.codigo = codigo; }
}
