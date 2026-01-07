package com.tp.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sucursal")
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=30)
    private String codigo;

    @Column(nullable=false, length=160)
    private String domicilio;

    @Column(nullable=false)
    private int nroEmpleados;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="banco_id", nullable=false)
    private Banco banco;

    public Long getId() { return id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public int getNroEmpleados() { return nroEmpleados; }
    public void setNroEmpleados(int nroEmpleados) { this.nroEmpleados = nroEmpleados; }

    public Banco getBanco() { return banco; }
    public void setBanco(Banco banco) { this.banco = banco; }
}
