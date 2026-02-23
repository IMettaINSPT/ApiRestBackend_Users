package com.tp.backend.dto.juicio;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class JuicioUpdateRequest {

    @NotBlank(message = "El expediente es obligatorio")
    private String expediente;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fechaJuicio;

    @NotNull(message = "La situación penal es obligatoria")
    private boolean condenado; // CAMBIO: boolean

    @NotNull(message = "El juez es obligatorio")
    private Long juezId;

    @NotNull(message = "El asalto es obligatorio")
    private Long asaltoId;

    @NotNull(message = "La persona es obligatoria")
    private Long personaDetenidaId;

    private LocalDate fechaInicioCondena;

    @Min(value = 1, message = "El tiempo de condena debe ser al menos 1 mes")
    private Integer tiempoCondenaMeses;

    public String getExpediente() { return expediente; }
    public void setExpediente(String expediente) { this.expediente = expediente; }

    public LocalDate getFechaJuicio() { return fechaJuicio; }
    public void setFechaJuicio(LocalDate fechaJuicio) { this.fechaJuicio = fechaJuicio; }

    public boolean isCondenado() { return condenado; } // CAMBIO: isCondenado
    public void setCondenado(boolean condenado) { this.condenado = condenado; }

    public Long getJuezId() { return juezId; }
    public void setJuezId(Long juezId) { this.juezId = juezId; }

    public Long getAsaltoId() { return asaltoId; }
    public void setAsaltoId(Long asaltoId) { this.asaltoId = asaltoId; }

    public Long getPersonaDetenidaId() { return personaDetenidaId; }
    public void setPersonaDetenidaId(Long personaDetenidaId) { this.personaDetenidaId = personaDetenidaId; }

    public LocalDate getFechaInicioCondena() { return fechaInicioCondena; }
    public void setFechaInicioCondena(LocalDate fechaInicioCondena) { this.fechaInicioCondena = fechaInicioCondena; }

    public Integer getTiempoCondenaMeses() { return tiempoCondenaMeses; }
    public void setTiempoCondenaMeses(Integer tiempoCondenaMeses) { this.tiempoCondenaMeses = tiempoCondenaMeses; }
}