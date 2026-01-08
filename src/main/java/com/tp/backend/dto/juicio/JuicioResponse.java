package com.tp.backend.dto.juicio;

import com.tp.backend.model.ResultadoJuicio;

import java.time.LocalDate;

public class JuicioResponse {

    private Long id;
    private LocalDate fecha;
    private ResultadoJuicio resultado;

    private Long juezId;
    private String juezCodigo;

    private Long personaDetenidaId;

    public JuicioResponse(Long id, LocalDate fecha, ResultadoJuicio resultado,
                          Long juezId, String juezCodigo,
                          Long personaDetenidaId) {
        this.id = id;
        this.fecha = fecha;
        this.resultado = resultado;
        this.juezId = juezId;
        this.juezCodigo = juezCodigo;
        this.personaDetenidaId = personaDetenidaId;
    }

    public Long getId() { return id; }
    public LocalDate getFecha() { return fecha; }
    public ResultadoJuicio getResultado() { return resultado; }
    public Long getJuezId() { return juezId; }
    public String getJuezCodigo() { return juezCodigo; }
    public Long getPersonaDetenidaId() { return personaDetenidaId; }
}
