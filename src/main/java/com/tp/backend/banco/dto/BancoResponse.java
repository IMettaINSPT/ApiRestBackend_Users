package com.tp.backend.banco.dto;

import com.tp.backend.sucursal.dto.SucursalResponse;
import java.util.List;

public class BancoResponse {

    private Long id;
    private String codigo;
    private String domicilioCentral;
    // Se agrega la lista para guardar las sucursales
    private List<SucursalResponse> sucursales;

    // Se agrega constructor vacío (necesario para frameworks de mapeo)
    public BancoResponse() {}


    public BancoResponse(Long id, String codigo, String domicilioCentral, List<SucursalResponse> sucursales) {
        this.id = id;
        this.codigo = codigo;
        this.domicilioCentral = domicilioCentral;
        this.sucursales = sucursales;
    }

    public Long getId() { return id;}
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDomicilioCentral() { return domicilioCentral; }
    public void setDomicilioCentral(String domicilioCentral) { this.domicilioCentral = domicilioCentral; }


    // Métodos fundamentales para que el Service no falle
    public List<SucursalResponse> getSucursales() { return sucursales; }
    public void setSucursales(List<SucursalResponse> sucursales) { this.sucursales = sucursales; }
}