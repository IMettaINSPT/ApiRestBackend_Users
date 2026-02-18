package com.tp.backend.dto.banda;

import com.tp.backend.dto.personaDetenida.PersonaDetenidaResponse;
import java.util.List;

public class BandaResponse {
    private Long id;
    private Integer numeroBanda;
    private Integer numeroMiembros;

    // Lista
    private List<PersonaDetenidaResponse> personasDetenidas;

    // CONSTRUCTOR VACÍO
    public BandaResponse() {}

    public BandaResponse(Long id, Integer numeroBanda, Integer numeroMiembros) {
        this.id = id;
        this.numeroBanda = numeroBanda;
        this.numeroMiembros = numeroMiembros;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getNumeroBanda() { return numeroBanda; }
    public void setNumeroBanda(Integer numeroBanda) { this.numeroBanda = numeroBanda; }

    public Integer getNumeroMiembros() { return numeroMiembros; }
    public void setNumeroMiembros(Integer numeroMiembros) { this.numeroMiembros = numeroMiembros; }


    // Getter y Setter para la lista
    public List<PersonaDetenidaResponse> getPersonasDetenidas() {
        return personasDetenidas;
    }
    public void setPersonasDetenidas(List<PersonaDetenidaResponse> personasDetenidas) {
        this.personasDetenidas = personasDetenidas;
    }
}
