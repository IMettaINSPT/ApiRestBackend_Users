package com.tp.backend.dto.banda;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class BandaUpdateRequest {
    @NotNull(message = "El número de banda es obligatorio")
    @Min(value = 1, message = "El número de banda debe ser mayor a 0")
    private Integer numeroBanda;

    @NotNull(message = "La cantidad de miembros es obligatoria")
    @Min(value = 2, message = "La banda debe tener al menos 2 miembros")
    @Max(value = 500, message = "La banda no puede tener más de 500 miembros")
    private Integer numeroMiembros;

    public Integer getNumeroBanda() {
        return numeroBanda;
    }
    public void setNumeroBanda(Integer numeroBanda) {
        this.numeroBanda = numeroBanda;
    }

    public Integer getNumeroMiembros() {
        return numeroMiembros;
    }
    public void setNumeroMiembros(Integer numeroMiembros) {
        this.numeroMiembros = numeroMiembros;
    }
}