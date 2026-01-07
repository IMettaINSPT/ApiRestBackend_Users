package com.tp.backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "banco")
public class Banco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=30)
    private String codigo;

    @Column(nullable=false, length=120)
    private String domicilioCentral;

    @OneToMany(mappedBy = "banco", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sucursal> sucursales = new ArrayList<>();

    public Long getId() { return id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDomicilioCentral() { return domicilioCentral; }
    public void setDomicilioCentral(String domicilioCentral) { this.domicilioCentral = domicilioCentral; }

    public List<Sucursal> getSucursales() { return sucursales; }
}
