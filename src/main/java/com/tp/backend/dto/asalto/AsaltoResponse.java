package com.tp.backend.dto.asalto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class AsaltoResponse {

    private Long id;
    private LocalDate fecha;

    private Long sucursalId;
    private String sucursalCodigo;

    private Long bandaId;
    private String bandaNombre;

    private Long vigilanteId;
    private String vigilanteCodigo;

    private BigDecimal montoRobado;

    private Set<Long> personasDetenidasIds;

    public AsaltoResponse(Long id, LocalDate fecha, BigDecimal montoRobado,
                          Long sucursalId, String sucursalCodigo,
                          Long bandaId, String bandaNombre,
                          Long vigilanteId, String vigilanteCodigo,
                          Set<Long> personasDetenidasIds) {
        this.id = id;
        this.fecha = fecha;
        this.montoRobado = montoRobado;
        this.sucursalId = sucursalId;
        this.sucursalCodigo = sucursalCodigo;
        this.bandaId = bandaId;
        this.bandaNombre = bandaNombre;
        this.vigilanteId = vigilanteId;
        this.vigilanteCodigo = vigilanteCodigo;
        this.personasDetenidasIds = personasDetenidasIds;
    }

    public Long getId() { return id; }
    public LocalDate getFecha() { return fecha; }
    public Long getSucursalId() { return sucursalId; }
    public String getSucursalCodigo() { return sucursalCodigo; }
    public Long getBandaId() { return bandaId; }
    public String getBandaNombre() { return bandaNombre; }
    public Long getVigilanteId() { return vigilanteId; }
    public String getVigilanteCodigo() { return vigilanteCodigo; }
    public Set<Long> getPersonasDetenidasIds() { return personasDetenidasIds; }
    public BigDecimal getMontoRobado() { return montoRobado; }

}
