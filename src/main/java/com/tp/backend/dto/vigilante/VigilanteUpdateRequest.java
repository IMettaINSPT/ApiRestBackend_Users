package com.tp.backend.dto.vigilante;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class VigilanteUpdateRequest {

    @NotBlank
    @Size(max = 30)
    private String codigo;

    @Min(value = 18, message = "La edad mínima es 18")
    @Max(value = 70, message = "La edad máxima es 70")
    private int edad;

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
}