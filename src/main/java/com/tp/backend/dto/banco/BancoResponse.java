package com.tp.backend.dto.banco;

public class BancoResponse {

    private Long id;
    private String codigo;
    private String domicilioCentral;

    public BancoResponse(Long id, String codigo, String domicilioCentral) {
        this.id = id;
        this.codigo = codigo;
        this.domicilioCentral = domicilioCentral;
    }

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getDomicilioCentral() { return domicilioCentral; }
}
