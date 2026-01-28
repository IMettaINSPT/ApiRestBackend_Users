package com.tp.backend.dto.banda;

public class BandaResponse {
    private Long id;
    private Integer numeroBanda;
    private Integer numeroMiembros;

    public BandaResponse(Long id, Integer numeroBanda, Integer numeroMiembros) {
        this.id = id;
        this.numeroBanda = numeroBanda;
        this.numeroMiembros = numeroMiembros;
    }

    public Long getId() { return id; }

    public Integer getNumeroBanda() {
        return numeroBanda;
    }

    public Integer getNumeroMiembros() {
        return numeroMiembros;
    }
}
