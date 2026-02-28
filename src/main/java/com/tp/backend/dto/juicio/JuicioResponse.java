package com.tp.backend.dto.juicio;

import com.tp.backend.dto.juez.JuezResponse;
import com.tp.backend.dto.personaDetenida.PersonaDetenidaResponse;
import com.tp.backend.dto.asalto.AsaltoResponse;
import java.time.LocalDate;

public class JuicioResponse {

    private Long id;
    private String expediente;
    private LocalDate fechaJuicio;
    private boolean condenado;
    private String detallePena;

    private JuezResponse juez;
    private AsaltoResponse asalto;
    private PersonaDetenidaResponse persona;

    public JuicioResponse() {}

    public JuicioResponse(Long id, String expediente, LocalDate fechaJuicio,
                          boolean condenado, String detallePena,
                          JuezResponse juez, AsaltoResponse asalto, PersonaDetenidaResponse persona) {
        this.id = id;
        this.expediente = expediente;
        this.fechaJuicio = fechaJuicio;
        this.condenado = condenado;
        this.detallePena = detallePena;
        this.juez = juez;
        this.asalto = asalto;
        this.persona = persona;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExpediente() { return expediente; }
    public void setExpediente(String expediente) { this.expediente = expediente; }

    public LocalDate getFechaJuicio() { return fechaJuicio; }
    public void setFechaJuicio(LocalDate fechaJuicio) { this.fechaJuicio = fechaJuicio; }

    public boolean isCondenado() { return condenado; } // CAMBIO: isCondenado
    public void setCondenado(boolean condenado) { this.condenado = condenado; }

    public String getDetallePena() { return detallePena; }
    public void setDetallePena(String detallePena) { this.detallePena = detallePena; }

    public JuezResponse getJuez() { return juez; }
    public void setJuez(JuezResponse juez) { this.juez = juez; }

    public AsaltoResponse getAsalto() { return asalto; }
    public void setAsalto(AsaltoResponse asalto) { this.asalto = asalto; }

    public PersonaDetenidaResponse getPersona() { return persona; }
    public void setPersona(PersonaDetenidaResponse persona) { this.persona = persona; }
}