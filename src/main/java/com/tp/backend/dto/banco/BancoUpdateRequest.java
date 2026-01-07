package com.tp.backend.dto.banco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BancoUpdateRequest {

    @NotBlank
    @Size(max = 30)
    private String codigo;

    @NotBlank
    @Size(max = 120)
    private String domicilioCentral;

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDomicilioCentral() { return domicilioCentral; }
    public void setDomicilioCentral(String domicilioCentral) { this.domicilioCentral = domicilioCentral; }
}
