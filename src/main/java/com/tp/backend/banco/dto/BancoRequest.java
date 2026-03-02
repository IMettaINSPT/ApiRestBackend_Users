package com.tp.backend.banco.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BancoRequest {
    @NotBlank(message = "El código es obligatorio")
    @Size(max = 30)

    private String codigo;

    @NotBlank(message = "El domicilio es obligatorio")
    @Size(max = 120)
    private String domicilioCentral;

    public String getCodigo() { return codigo; }
    public void setCodigo(String c) { this.codigo = c; }
    public String getDomicilioCentral() { return domicilioCentral; }
    public void setDomicilioCentral(String d) { this.domicilioCentral = d; }
}