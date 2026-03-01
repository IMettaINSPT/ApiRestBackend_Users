package com.tp.backend.dto.personaDetenida;

import com.tp.backend.banda.dto.BandaResponse;
import java.util.List;
import com.tp.backend.dto.asalto.AsaltoResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public record PersonaDetenidaResponse(
        Long id,
        String codigo,
        String nombre,
        String apellido,
        BandaResponse banda,

        // Cortamos la recursividad aquí
        @JsonIgnoreProperties("personas")
        List<AsaltoResponse> asaltos
) {}
