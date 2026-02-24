package com.tp.backend.dto.contrato;

import java.time.LocalDate;

public class ContratoResponse {

    private Long id;
    private String numContrato;
    private LocalDate fechaContrato;
    private boolean conArma;

    private Long sucursalId;
    private String sucursalCodigo;

    private Long vigilanteId;
    private String vigilanteCodigo;
    private LocalDate fechaFin;
    private String domicilioSucursal;

    public ContratoResponse(Long id, String numContrato, LocalDate fechaContrato, boolean conArma,
                            Long sucursalId, String sucursalCodigo,
                            Long vigilanteId, String vigilanteCodigo, LocalDate fechaFin,String domicilioSucursal) {
        this.id = id;
        this.numContrato = numContrato;
        this.fechaContrato = fechaContrato;
        this.conArma = conArma;
        this.sucursalId = sucursalId;
        this.sucursalCodigo = sucursalCodigo;
        this.vigilanteId = vigilanteId;
        this.vigilanteCodigo = vigilanteCodigo;
        this.fechaFin = fechaFin;
        this.domicilioSucursal = domicilioSucursal;
    }

    public Long getId() { return id; }

    //  los Getters y Setters para numContrato
    public String getNumContrato() { return numContrato; }
    public void setNumContrato(String numContrato) { this.numContrato = numContrato; }

    public LocalDate getFechaContrato() { return fechaContrato; }
    public boolean isConArma() { return conArma; }
    public Long getSucursalId() { return sucursalId; }
    public String getSucursalCodigo() { return sucursalCodigo; }
    public Long getVigilanteId() { return vigilanteId; }
    public String getVigilanteCodigo() { return vigilanteCodigo; }


    public LocalDate getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDomicilioSucursal() { return domicilioSucursal; }


}
