package com.tp.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.math.BigDecimal;

@Entity
@Table(name = "asalto")
public class Asalto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;


    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal montoRobado;


    public BigDecimal getMontoRobado() { return montoRobado; }
    public void setMontoRobado(BigDecimal montoRobado) { this.montoRobado = montoRobado; }


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "banda_id", nullable = false)
    private Banda banda;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "vigilante_id", nullable = false)
    private Vigilante vigilante;

    @ManyToMany
    @JoinTable(
            name = "asalto_detenidos",
            joinColumns = @JoinColumn(name = "asalto_id"),
            inverseJoinColumns = @JoinColumn(name = "persona_detenida_id")
    )

    private Set<PersonaDetenida> personasDetenidas = new HashSet<>();

    public Long getId() { return id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }

    public Banda getBanda() { return banda; }
    public void setBanda(Banda banda) { this.banda = banda; }

    public Vigilante getVigilante() { return vigilante; }
    public void setVigilante(Vigilante vigilante) { this.vigilante = vigilante; }

    public Set<PersonaDetenida> getPersonasDetenidas() { return personasDetenidas; }
}
