package com.tp.backend.dto.personaDetenida;

public class PersonaDetenidaResponse {
    private Long id;
    private String dni;
    private String nombre;

    private Long bandaId;
    private String bandaNombre;

    public PersonaDetenidaResponse(Long id, String dni, String nombre, Long bandaId, String bandaNombre) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.bandaId = bandaId;
        this.bandaNombre = bandaNombre;
    }

    public Long getId() { return id; }
    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public Long getBandaId() { return bandaId; }
    public String getBandaNombre() { return bandaNombre; }
}
