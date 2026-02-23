package com.tp.backend.dto.juicio;

import com.tp.backend.model.PersonaDetenida;
import com.tp.backend.model.ResultadoJuicio;
import com.tp.backend.dto.juez.JuezResponse;
import com.tp.backend.dto.personaDetenida.PersonaDetenidaResponse;
import com.tp.backend.dto.asalto.AsaltoResponse;
import java.time.LocalDate;

public class JuicioResponse {

    private Long id;
    private String expediente;      // Agregado para j.expediente
    private LocalDate fechaJuicio;
    private ResultadoJuicio situacionPenal;
    private String detallePena;     // Aquí viajará el texto: "Cumplió X meses..."

    // Cambiamos los IDs por los objetos Response completos para que el HTML navegue
    private JuezResponse juez;
    private AsaltoResponse asalto;
    private PersonaDetenidaResponse persona;

    // Constructor vacío (necesario para frameworks)
    public JuicioResponse() {}

    // Constructor completo actualizado
    public JuicioResponse(Long id, String expediente, LocalDate fechaJuicio,
                          ResultadoJuicio situacionPenal, String detallePena,
                          JuezResponse juez, AsaltoResponse asalto, PersonaDetenidaResponse persona) {
        this.id = id;
        this.expediente = expediente;
        this.fechaJuicio = fechaJuicio;
        this.situacionPenal = situacionPenal;
        this.detallePena = detallePena;
        this.juez = juez;
        this.asalto = asalto;
        this.persona = persona;
    }

    // --- GETTERS Y SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExpediente() { return expediente; }
    public void setExpediente(String expediente) { this.expediente = expediente; }

    public LocalDate getFechaJuicio() { return fechaJuicio; }
    public void setFechaJuicio(LocalDate fechaJuicio) { this.fechaJuicio = fechaJuicio; }

    public ResultadoJuicio getSituacionPenal() { return situacionPenal; }
    public void setSituacionPenal(ResultadoJuicio situacionPenal) { this.situacionPenal = situacionPenal; }

    public String getDetallePena() { return detallePena; }
    public void setDetallePena(String detallePena) { this.detallePena = detallePena; }

    public JuezResponse getJuez() { return juez; }
    public void setJuez(JuezResponse juez) { this.juez = juez; }

    public AsaltoResponse getAsalto() { return asalto; }
    public void setAsalto(AsaltoResponse asalto) { this.asalto = asalto; }

    public PersonaDetenidaResponse getPersona() { return persona; }
    public void setPersona(PersonaDetenidaResponse persona) { this.persona = persona; }
}