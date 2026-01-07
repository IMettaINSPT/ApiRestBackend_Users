package com.tp.backend.dto.banda;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BandaRequest {
    @NotBlank @Size(max=60)
    private String nombre;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
