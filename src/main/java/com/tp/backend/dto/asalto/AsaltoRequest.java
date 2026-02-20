package com.tp.backend.dto.asalto;

import java.time.LocalDate;
import java.util.List;

public class AsaltoRequest {
    private String codigo;
    private LocalDate fechaAsalto;
    private Long sucursalId;
    private List<Long> personaDetenidaIds;

    public LocalDate getFechaAsalto() { return fechaAsalto; }
    public void setFechaAsalto(LocalDate fechaAsalto) { this.fechaAsalto = fechaAsalto; }

    public Long getSucursalId() { return sucursalId; }
    public void setSucursalId(Long sucursalId) { this.sucursalId = sucursalId; }


    public List<Long> getPersonaDetenidaIds() {return personaDetenidaIds;}
    public void setPersonaDetenidaIds(List<Long> personaDetenidaIds) {this.personaDetenidaIds = personaDetenidaIds;}
}
