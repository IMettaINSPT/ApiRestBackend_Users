package com.tp.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;




@Entity
@Table(name = "asalto")
public class Asalto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", unique = true)
    private String codigo;

    @Column(name = "fecha_asalto", nullable = false)
    private LocalDate fechaAsalto;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;

    // Relación de Muchos a Muchos
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "asalto_persona", // Tabla intermedia
            joinColumns = @JoinColumn(name = "asalto_id"),
            inverseJoinColumns = @JoinColumn(name = "persona_id")
    )
    private java.util.List<PersonaDetenida> personas = new java.util.ArrayList<>();

    // Getters y Setters
    public Long getId() { return id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public LocalDate getFechaAsalto() { return fechaAsalto; }
    public void setFechaAsalto(LocalDate fechaAsalto) { this.fechaAsalto = fechaAsalto; }

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }

    public java.util.List<PersonaDetenida> getPersonas() { return personas; }
    public void setPersonas(java.util.List<PersonaDetenida> personas) { this.personas = personas; }
}