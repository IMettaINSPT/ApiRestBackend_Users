package com.tp.backend.dto.personaDetenida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PersonaDetenidaUpdateRequest {

    @NotBlank @Size(max=30)
    private String dni;

    @NotBlank @Size(max=80)
    private String nombre;

    private Long bandaId; // opcional (null = sin banda)

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Long getBandaId() { return bandaId; }
    public void setBandaId(Long bandaId) { this.bandaId = bandaId; }
}
