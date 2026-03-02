package com.tp.backend.banco.domain;

import com.tp.backend.sucursal.domain.Sucursal;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "banco")
public class Banco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private String domicilioCentral;

    @OneToMany(mappedBy = "banco", fetch = FetchType.LAZY)
    private List<Sucursal> sucursales = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getDomicilioCentral() { return domicilioCentral; }
    public void setDomicilioCentral(String domicilioCentral) { this.domicilioCentral = domicilioCentral; }
    public List<Sucursal> getSucursales() { return sucursales; }
    public void setSucursales(List<Sucursal> sucursales) { this.sucursales = sucursales; }
}