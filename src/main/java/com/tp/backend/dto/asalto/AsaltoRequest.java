package com.tp.backend.dto.asalto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;


public class AsaltoRequest implements AsaltoRequestBase {

    @NotNull
    private LocalDate fecha;

    @NotNull
    @DecimalMin(value = "0.01", message = "El monto robado debe ser mayor a 0")
    private BigDecimal montoRobado;

    @NotNull
    private Long sucursalId;

    @NotNull
    private Long bandaId;

    @NotNull
    private Long vigilanteId;

    @NotEmpty(message = "Debe haber al menos una persona detenida")
    private Set<Long> personasDetenidasIds;

    public BigDecimal getMontoRobado() { return montoRobado; }
    public void setMontoRobado(BigDecimal montoRobado) { this.montoRobado = montoRobado; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Long getSucursalId() { return sucursalId; }
    public void setSucursalId(Long sucursalId) { this.sucursalId = sucursalId; }

    public Long getBandaId() { return bandaId; }
    public void setBandaId(Long bandaId) { this.bandaId = bandaId; }

    public Long getVigilanteId() { return vigilanteId; }
    public void setVigilanteId(Long vigilanteId) { this.vigilanteId = vigilanteId; }

    public Set<Long> getPersonasDetenidasIds() { return personasDetenidasIds; }
    public void setPersonasDetenidasIds(Set<Long> personasDetenidasIds) {
        this.personasDetenidasIds = personasDetenidasIds;
    }
}
