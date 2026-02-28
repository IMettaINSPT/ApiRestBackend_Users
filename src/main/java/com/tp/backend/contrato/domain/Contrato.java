package com.tp.backend.contrato.domain;

import com.tp.backend.model.Sucursal;
import com.tp.backend.model.Vigilante;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "contrato")
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numContrato;
    private LocalDate fechaContrato;
    private LocalDate fechaFin;
    private boolean conArma;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Sucursal sucursal;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Vigilante vigilante;

    // Getters y Setters (los mismos que ya tenías)
    public Long getId() { return id; }
    public String getNumContrato() { return numContrato; }
    public void setNumContrato(String numContrato) { this.numContrato = numContrato; }
    public LocalDate getFechaContrato() { return fechaContrato; }
    public void setFechaContrato(LocalDate f) { this.fechaContrato = f; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate f) { this.fechaFin = f; }
    public boolean isConArma() { return conArma; }
    public void setConArma(boolean c) { this.conArma = c; }
    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal s) { this.sucursal = s; }
    public Vigilante getVigilante() { return vigilante; }
    public void setVigilante(Vigilante v) { this.vigilante = v; }
}