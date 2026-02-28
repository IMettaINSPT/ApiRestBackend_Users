package com.tp.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "contrato")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "num_contrato", unique = true)
    private String numContrato; // El número de negocio (autogenerado por lógica)

    @Column(nullable=false)
    private LocalDate fechaContrato;

<<<<<<< HEAD
    @Column(name = "fecha_fin")
=======
>>>>>>> fixes_euge
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

    // EL GETTER: Para que el sistema pueda leer el número y mostrarlo
    public String getNumContrato() {
        return numContrato;
    }

    // EL SETTER: Para que el sistema pueda asignar el número automático
    public void setNumContrato(String numContrato) {
        this.numContrato = numContrato;
    }

    public LocalDate getFechaContrato() { return fechaContrato; }
    public void setFechaContrato(LocalDate fechaContrato) { this.fechaContrato = fechaContrato; }

    public LocalDate getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

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
