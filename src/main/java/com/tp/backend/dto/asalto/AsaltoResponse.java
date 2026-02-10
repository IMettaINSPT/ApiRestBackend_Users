package com.tp.backend.dto.asalto;

import java.time.LocalDate;

public class AsaltoResponse {
    private Long id;
    private LocalDate fechaAsalto;
    private Long sucursalId;
    private String sucursalCodigo;
    private Long personaDetenidaId;
    private String personacodigo;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFechaAsalto() { return fechaAsalto; }
    public void setFechaAsalto(LocalDate fechaAsalto) { this.fechaAsalto = fechaAsalto; }

    public Long getSucursalId() { return sucursalId; }
    public void setSucursalId(Long sucursalId) { this.sucursalId = sucursalId; }

    public Long getPersonaDetenidaId() { return personaDetenidaId; }
    public void setPersonaDetenidaId(Long personaDetenidaId) { this.personaDetenidaId = personaDetenidaId; }

    public String getSucursalCodigo() {
        return sucursalCodigo;
    }

    public void setSucursalCodigo(String sucursalCodigo) {
        this.sucursalCodigo = sucursalCodigo;
    }

    public String getPersonacodigo() {
        return personacodigo;
    }

    public void setPersonacodigo(String personacodigo) {
        this.personacodigo = personacodigo;
    }
}
