package com.tp.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "personaDetenida")
public class PersonaDetenida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=30)
    private String dni;

    @Column(nullable=false, length=80)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="banda_id")
    private Banda banda; // opcional

    public Long getId() { return id; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Banda getBanda() { return banda; }
    public void setBanda(Banda banda) { this.banda = banda; }
}
