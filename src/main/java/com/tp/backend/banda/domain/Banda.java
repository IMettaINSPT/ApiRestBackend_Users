package com.tp.backend.banda.domain;

import com.tp.backend.personaDetenida.domain.PersonaDetenida;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "banda")
public class Banda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer numeroBanda;

    @Column(nullable = false, unique = false)
    private Integer numeroMiembros;

    @OneToMany(mappedBy = "banda", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PersonaDetenida> personasDetenidas = new ArrayList<>();

    // Getters y Setters
    public Long getId() { return id; }
    public Integer getNumeroBanda() { return numeroBanda; }
    public void setNumeroBanda(Integer n) { this.numeroBanda = n; }
    public Integer getNumeroMiembros() { return numeroMiembros; }
    public void setNumeroMiembros(Integer n) { this.numeroMiembros = n; }
    public List<PersonaDetenida> getPersonasDetenidas() { return personasDetenidas; }
}
