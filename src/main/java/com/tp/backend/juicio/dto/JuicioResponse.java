package com.tp.backend.juicio.dto;

import com.tp.backend.juez.dto.JuezResponse;
import com.tp.backend.personaDetenida.dto.PersonaDetenidaResponse;
import com.tp.backend.asalto.dto.AsaltoResponse;
import java.time.LocalDate;

public class JuicioResponse {

    private Long id;
    private String expediente;
    private LocalDate fechaJuicio;
    private boolean condenado;
    private String detallePena;

    // AGREGAR ESTOS DOS CAMPOS QUE FALTABAN:
    private LocalDate fechaInicioCondena;
    private Integer tiempoCondenaMeses;

    private JuezResponse juez;
    private AsaltoResponse asalto;
    private PersonaDetenidaResponse persona;

    public JuicioResponse() {}

    // Getters y Setters para los campos existentes
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExpediente() { return expediente; }
    public void setExpediente(String expediente) { this.expediente = expediente; }

    public LocalDate getFechaJuicio() { return fechaJuicio; }
    public void setFechaJuicio(LocalDate fechaJuicio) { this.fechaJuicio = fechaJuicio; }

    public boolean isCondenado() { return condenado; }
    public void setCondenado(boolean condenado) { this.condenado = condenado; }

    public String getDetallePena() { return detallePena; }
    public void setDetallePena(String detallePena) { this.detallePena = detallePena; }

    // NUEVOS SETTERS Y GETTERS QUE EL MAPPER NECESITA:
    public LocalDate getFechaInicioCondena() { return fechaInicioCondena; }
    public void setFechaInicioCondena(LocalDate fechaInicioCondena) {
        this.fechaInicioCondena = fechaInicioCondena;
    }

    public Integer getTiempoCondenaMeses() { return tiempoCondenaMeses; }
    public void setTiempoCondenaMeses(Integer tiempoCondenaMeses) {
        this.tiempoCondenaMeses = tiempoCondenaMeses;
    }

    public JuezResponse getJuez() { return juez; }
    public void setJuez(JuezResponse juez) { this.juez = juez; }

    public AsaltoResponse getAsalto() { return asalto; }
    public void setAsalto(AsaltoResponse asalto) { this.asalto = asalto; }

    public PersonaDetenidaResponse getPersona() { return persona; }
    public void setPersona(PersonaDetenidaResponse persona) { this.persona = persona; }
}