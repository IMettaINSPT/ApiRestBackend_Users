package com.tp.backend.banda.mapper;

import com.tp.backend.banda.domain.Banda;
import com.tp.backend.banda.dto.BandaResponse;
import com.tp.backend.banda.dto.BandaRequest;
import com.tp.backend.dto.personaDetenida.PersonaDetenidaResponse;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class BandaMapper {
    public BandaResponse toResponse(Banda b) {
        BandaResponse res = new BandaResponse(b.getId(), b.getNumeroBanda(), b.getNumeroMiembros());
        if (b.getPersonasDetenidas() != null) {
            List<PersonaDetenidaResponse> integrantes = b.getPersonasDetenidas().stream()
                    .map(p -> new PersonaDetenidaResponse(
                            p.getId(), p.getCodigo(), p.getNombre(), p.getApellido(), null, null
                    )).toList();
            res.setPersonasDetenidas(integrantes);
        }
        return res;
    }

    public Banda toEntity(BandaRequest req) {
        Banda b = new Banda();
        b.setNumeroBanda(req.getNumeroBanda());
        b.setNumeroMiembros(req.getNumeroMiembros());
        return b;
    }
}
