package com.tp.backend.dto.asalto;

import com.tp.backend.dto.sucursal.SucursalResponse;

import java.time.LocalDate;
import java.util.List;
import com.tp.backend.dto.personaDetenida.PersonaDetenidaResponse;//  importar DTO de persona detenida
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class AsaltoResponse {
    private Long id;
    private String codigo;
    private LocalDate fechaAsalto;
<<<<<<< HEAD
    private Long sucursalId;
    private String sucursalCodigo;
    private Long personaDetenidaId;
    private String personacodigo;
=======

    // RELACIÓN 1 a 1: Cada asalto ocurre en UNA sola sucursal
    private SucursalResponse sucursal;

    // Lista - RELACIÓN 1 a N: Un asalto es cometido por MUCHAS personas
    // Cortamos la recursividad acá
    @JsonIgnoreProperties("asaltos")
    private List<PersonaDetenidaResponse> personas;


>>>>>>> fixes_euge


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public LocalDate getFechaAsalto() { return fechaAsalto; }
    public void setFechaAsalto(LocalDate fechaAsalto) { this.fechaAsalto = fechaAsalto; }


<<<<<<< HEAD
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
=======
    public SucursalResponse getSucursal() { return sucursal; }
    public void setSucursal(SucursalResponse sucursal) { this.sucursal = sucursal; }


    // Getter y Setter para la lista
    public List<PersonaDetenidaResponse> getPersonas() {
        return personas;
    }
    public void setPersonas(List<PersonaDetenidaResponse> personas) {
        this.personas = personas;
>>>>>>> fixes_euge
    }
}
