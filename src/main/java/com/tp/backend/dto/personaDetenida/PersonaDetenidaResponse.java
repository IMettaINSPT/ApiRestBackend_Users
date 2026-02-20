package com.tp.backend.dto.PersonaDetenida;

import com.tp.backend.dto.Banda.BandaResponse;
import java.util.List;
import com.tp.backend.dto.Asalto.AsaltoResponse;

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
