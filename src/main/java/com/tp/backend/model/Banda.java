package com.tp.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "banda")
public class Banda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=60)
    private String nombre;

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
