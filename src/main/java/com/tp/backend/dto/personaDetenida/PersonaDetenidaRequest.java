package com.tp.backend.dto.personaDetenida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PersonaDetenidaRequest {

    @NotBlank @Size(max=30)
    private String codigo;

    @NotBlank @Size(max=80)
    private String nombre;

    @NotBlank @Size(max=100)
    private String apellido;

    private Long bandaId; // opcional

    public String getcodigo() { return codigo; }
    public void setcodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public Long getBandaId() { return bandaId; }
    public void setBandaId(Long bandaId) { this.bandaId = bandaId; }
}
