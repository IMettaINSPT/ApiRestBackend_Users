package com.tp.backend.dto.juez;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class JuezRequest {

    @NotBlank
    @Size(max = 10)
    private String claveJuzgado;

    @NotBlank
    @Size(max = 80)
    private String nombre;

    @NotBlank
    @Size(max = 80)
    private String apellido;

    @NotNull(message = "Los años de servicio son obligatorios")
    @Min(value = 0, message = "Los años de servicio mínimos son 0")
    @Max(value = 45, message = "Los años de servicio máximos son 45")
    private Integer anosServicio;


    public String getClaveJuzgado() { return claveJuzgado; }
    public void setClaveJuzgado(String claveJuzgado) { this.claveJuzgado = claveJuzgado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public Integer getAnosServicio() { return anosServicio; }
    public void setAnosServicio(Integer anosServicio) { this.anosServicio = anosServicio; }

}