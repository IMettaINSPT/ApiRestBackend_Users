package com.tp.backend.dto.juicio;

import com.tp.backend.model.ResultadoJuicio;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class JuicioUpdateRequest {

    @NotNull
    private LocalDate fecha;

    @NotNull
    private ResultadoJuicio resultado;

    @NotNull
    private Long juezId;

    @NotNull
    private Long personaDetenidaId;

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public ResultadoJuicio getResultado() { return resultado; }
    public void setResultado(ResultadoJuicio resultado) { this.resultado = resultado; }

    public Long getJuezId() { return juezId; }
    public void setJuezId(Long juezId) { this.juezId = juezId; }

    public Long getPersonaDetenidaId() { return personaDetenidaId; }
    public void setPersonaDetenidaId(Long personaDetenidaId) {
        this.personaDetenidaId = personaDetenidaId;
    }
}
