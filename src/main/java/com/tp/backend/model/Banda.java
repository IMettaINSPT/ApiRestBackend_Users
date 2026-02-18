package com.tp.backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "banda")
public class Banda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private Integer numeroBanda;

    @Column(nullable=false, unique=false)
    private Integer numeroMiembros;

    // Se agrega fetch = FetchType.EAGER para que cargue las personas detenidas automáticamente
    @OneToMany(mappedBy = "banda", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PersonaDetenida> personasDetenidas = new ArrayList<>();

    public Long getId() { return id; }

    public Integer getNumeroBanda() {
        return numeroBanda;
    }
    public void setNumeroBanda(Integer numeroBanda) {
        this.numeroBanda = numeroBanda;
    }

    public Integer getNumeroMiembros() {
        return numeroMiembros;
    }
    public void setNumeroMiembros(Integer numeroMiembros) {
        this.numeroMiembros = numeroMiembros;
    }

    public List<PersonaDetenida> getPersonasDetenidas() { return personasDetenidas; }

}
