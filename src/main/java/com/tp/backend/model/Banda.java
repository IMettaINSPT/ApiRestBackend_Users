package com.tp.backend.model;

import jakarta.persistence.*;

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
}
