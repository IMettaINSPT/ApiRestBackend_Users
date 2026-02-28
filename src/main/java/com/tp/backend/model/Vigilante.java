package com.tp.backend.model;

import com.tp.backend.contrato.domain.Contrato;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vigilante")
public class Vigilante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=30)
    private String codigo;

    @Column(nullable=false)
    private int edad;

    @OneToMany(mappedBy = "vigilante", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Contrato> contratos = new HashSet<>();

    public Long getId() { return id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public Set<Contrato> getContratos() {return contratos; }
}
