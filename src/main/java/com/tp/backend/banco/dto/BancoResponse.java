package com.tp.backend.banco.dto;

import com.tp.backend.sucursal.dto.SucursalResponse; // Importante: usar el DTO, no el dominio
import java.util.List;

public class BancoResponse {
    private Long id;
    private String codigo;
    private String domicilioCentral;
    private List<SucursalResponse> sucursales; // Cambiado de List<String> a List<SucursalResponse>

    public BancoResponse(Long id, String codigo, String domicilioCentral, List<SucursalResponse> sucursales) {
        this.id = id;
        this.codigo = codigo;
        this.domicilioCentral = domicilioCentral;
        this.sucursales = sucursales;
    }

    // Getters
    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getDomicilioCentral() { return domicilioCentral; }
    public List<SucursalResponse> getSucursales() { return sucursales; }
}