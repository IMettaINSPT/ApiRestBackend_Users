package com.tp.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(
        name = "asalto",
        uniqueConstraints = @UniqueConstraint(columnNames = {"sucursal_id", "persona_detenida_id", "fecha_asalto"})
)
public class Asalto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_asalto", nullable = false)
    private LocalDate fechaAsalto;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_detenida_id", nullable = false)
    private PersonaDetenida personaDetenida;

    public Long getId() { return id; }

    public LocalDate getFechaAsalto() { return fechaAsalto; }
    public void setFechaAsalto(LocalDate fechaAsalto) { this.fechaAsalto = fechaAsalto; }

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }

    public PersonaDetenida getPersonaDetenida() { return personaDetenida; }
    public void setPersonaDetenida(PersonaDetenida personaDetenida) { this.personaDetenida = personaDetenida; }
}

