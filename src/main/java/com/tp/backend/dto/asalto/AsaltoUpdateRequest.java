package com.tp.backend.dto.asalto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class AsaltoUpdateRequest {

    @NotNull(message = "El código es obligatorio")
    private String codigo;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fechaAsalto; // Coincide con el Front

    @NotNull(message = "La sucursal es obligatoria")
    private Long sucursalId;

    @NotEmpty(message = "Debe seleccionar al menos una persona")
    private List<Long> personaDetenidaIds; // Coincide con el Front

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public LocalDate getFechaAsalto() { return fechaAsalto; }
    public void setFechaAsalto(LocalDate fechaAsalto) { this.fechaAsalto = fechaAsalto; }

    public Long getSucursalId() { return sucursalId; }
    public void setSucursalId(Long sucursalId) { this.sucursalId = sucursalId; }

    public List<Long> getPersonaDetenidaIds() { return personaDetenidaIds; }
    public void setPersonaDetenidaIds(List<Long> personaDetenidaIds) {
        this.personaDetenidaIds = personaDetenidaIds;
    }
}