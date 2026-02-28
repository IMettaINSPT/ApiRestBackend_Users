package com.tp.backend.dto.vigilante;

import com.tp.backend.contrato.dto.ContratoResponse;
import java.util.List;

public class VigilanteResponse {

    private Long id;
    private String codigo;
    private int edad;

    // Se agrega la lista para guardar las sucursales
    private List<ContratoResponse> contratos;

    // Se agrega constructor vacío (necesario para frameworks de mapeo)
    public VigilanteResponse() {}

    public VigilanteResponse(Long id, String codigo, int edad) {
        this.id = id;
        this.codigo = codigo;
        this.edad = edad;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    // Métodos fundamentales para que el Service no falle
    public List<ContratoResponse> getContratos() { return contratos; }
    public void setContratos(List<ContratoResponse> contratos) { this.contratos = contratos; }

}
