package com.tp.backend.dto.contrato;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ContratoUpdateRequest {

    @NotNull
    private LocalDate fechaContrato;

    private boolean conArma;
    private LocalDate fechaFin; // opcional

    @NotNull
    private Long sucursalId;

    @NotNull
    private Long vigilanteId;

    public LocalDate getFechaContrato() { return fechaContrato; }
    public void setFechaContrato(LocalDate fechaContrato) { this.fechaContrato = fechaContrato; }

    public boolean isConArma() { return conArma; }
    public void setConArma(boolean conArma) { this.conArma = conArma; }

    public Long getSucursalId() { return sucursalId; }
    public void setSucursalId(Long sucursalId) { this.sucursalId = sucursalId; }

    public Long getVigilanteId() { return vigilanteId; }
    public void setVigilanteId(Long vigilanteId) { this.vigilanteId = vigilanteId; }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
