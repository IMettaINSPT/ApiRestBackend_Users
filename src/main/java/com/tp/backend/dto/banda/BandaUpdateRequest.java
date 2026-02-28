package com.tp.backend.dto.banda;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BandaUpdateRequest {
    @NotNull
    @Min(1)
    private Integer numeroBanda;

    @NotNull
    @Min(1)
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
