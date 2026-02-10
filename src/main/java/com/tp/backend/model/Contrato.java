package com.tp.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "contrato")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private LocalDate fechaContrato;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(nullable=false)
    private boolean conArma;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="sucursal_id", nullable=false)
    private Sucursal sucursal;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="vigilante_id", nullable=false)
    private Vigilante vigilante;

    public Long getId() { return id; }

    public LocalDate getFechaContrato() { return fechaContrato; }
    public void setFechaContrato(LocalDate fechaContrato) { this.fechaContrato = fechaContrato; }

    public boolean isConArma() { return conArma; }
    public void setConArma(boolean conArma) { this.conArma = conArma; }

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }

    public Vigilante getVigilante() { return vigilante; }
    public void setVigilante(Vigilante vigilante) { this.vigilante = vigilante; }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
