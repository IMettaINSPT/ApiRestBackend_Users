package com.tp.backend.dto.personaDetenida;

import com.tp.backend.dto.banda.BandaResponse;

public record PersonaDetenidaResponse (
     Long id,
     String codigo,
     String nombre,
     String apellido,
     BandaResponse banda ){

}
