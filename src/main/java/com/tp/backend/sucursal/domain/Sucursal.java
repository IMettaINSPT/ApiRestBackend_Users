package com.tp.backend.sucursal.domain;

import com.tp.backend.banco.domain.Banco;
import jakarta.persistence.*;

@Entity
@Table(name = "sucursal")
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private String domicilio;
    private int numEmpleados;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Banco banco;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }
    public int getNumEmpleados() { return numEmpleados; }
    public void setNumEmpleados(int num) { this.numEmpleados = num; }
    public Banco getBanco() { return banco; }
    public void setBanco(Banco banco) { this.banco = banco; }
}