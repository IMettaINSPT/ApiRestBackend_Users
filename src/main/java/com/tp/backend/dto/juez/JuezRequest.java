package com.tp.backend.dto.juez;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class JuezRequest {

    @NotBlank
    @Size(max = 30)
    private String codigo;

    @NotBlank
    @Size(max = 80)
    private String nombre;

    @NotBlank
    @Size(max = 80)
    private String apellido;

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
}
