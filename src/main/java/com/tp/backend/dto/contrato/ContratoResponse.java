package com.tp.backend.dto.contrato;

import java.time.LocalDate;

public class ContratoResponse {

    private Long id;
    private LocalDate fechaContrato;
    private boolean conArma;

    private Long sucursalId;
    private String sucursalCodigo;

    private Long vigilanteId;
    private String vigilanteCodigo;

    public ContratoResponse(Long id, LocalDate fechaContrato, boolean conArma,
                            Long sucursalId, String sucursalCodigo,
                            Long vigilanteId, String vigilanteCodigo) {
        this.id = id;
        this.fechaContrato = fechaContrato;
        this.conArma = conArma;
        this.sucursalId = sucursalId;
        this.sucursalCodigo = sucursalCodigo;
        this.vigilanteId = vigilanteId;
        this.vigilanteCodigo = vigilanteCodigo;
    }

    public Long getId() { return id; }
    public LocalDate getFechaContrato() { return fechaContrato; }
    public boolean isConArma() { return conArma; }
    public Long getSucursalId() { return sucursalId; }
    public String getSucursalCodigo() { return sucursalCodigo; }
    public Long getVigilanteId() { return vigilanteId; }
    public String getVigilanteCodigo() { return vigilanteCodigo; }
}
