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
<<<<<<< HEAD
    private LocalDate fechaFin; // opcional
=======
    private LocalDate fechaFin;
    private String domicilioSucursal;
>>>>>>> fixes_euge

    public ContratoResponse(Long id, String numContrato, LocalDate fechaContrato, boolean conArma,
                            Long sucursalId, String sucursalCodigo,
<<<<<<< HEAD
                            Long vigilanteId, String vigilanteCodigo, LocalDate fechafin) {
=======
                            Long vigilanteId, String vigilanteCodigo, LocalDate fechaFin,String domicilioSucursal) {
>>>>>>> fixes_euge
        this.id = id;
        this.numContrato = numContrato;
        this.fechaContrato = fechaContrato;
        this.conArma = conArma;
        this.sucursalId = sucursalId;
        this.sucursalCodigo = sucursalCodigo;
        this.vigilanteId = vigilanteId;
        this.vigilanteCodigo = vigilanteCodigo;
<<<<<<< HEAD
        this.fechaFin= fechafin;
=======
        this.fechaFin = fechaFin;
        this.domicilioSucursal = domicilioSucursal;
>>>>>>> fixes_euge
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

<<<<<<< HEAD
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
=======

    public LocalDate getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDomicilioSucursal() { return domicilioSucursal; }


>>>>>>> fixes_euge
}
