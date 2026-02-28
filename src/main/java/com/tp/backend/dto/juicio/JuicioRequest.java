package com.tp.backend.dto.juicio;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class JuicioRequest {

    @NotBlank(message = "El número de expediente es obligatorio")
    private String expediente;

    @NotNull(message = "La fecha del juicio es obligatoria")
    private LocalDate fechaJuicio;

    // CAMBIO: Se usa boolean para indicar si fue condenado (true) o no (false)
    @NotNull(message = "Debe indicar si la persona fue condenada o no")
    private boolean condenado;

    @NotNull(message = "El juez es obligatorio")
    private Long juezId;

    @NotNull(message = "El asalto es obligatorio")
    private Long asaltoId;

    @NotNull(message = "La persona es obligatoria")
    private Long personaDetenidaId;

    private LocalDate fechaInicioCondena;

    @Min(value = 1, message = "El tiempo de condena debe ser al menos 1 mes")
    @Max(value = 600, message = "El tiempo de condena no puede superar los 600 meses")
    private Integer tiempoCondenaMeses;

    public JuicioRequest() {}

    public String getExpediente() { return expediente; }
    public void setExpediente(String expediente) { this.expediente = expediente; }

    public LocalDate getFechaJuicio() { return fechaJuicio; }
    public void setFechaJuicio(LocalDate fechaJuicio) { this.fechaJuicio = fechaJuicio; }

    // Getter y Setter para el boolean
    public boolean isCondenado() { return condenado; }
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