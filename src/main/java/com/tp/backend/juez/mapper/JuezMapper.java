package com.tp.backend.juez.mapper;

import com.tp.backend.juez.domain.Juez;
import com.tp.backend.juez.dto.*;
import com.tp.backend.juicio.dto.JuicioResponse;
import com.tp.backend.personaDetenida.dto.PersonaDetenidaResponse;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JuezMapper {
    public JuezResponse toResponse(Juez j) {
        JuezResponse response = new JuezResponse(j.getId(), j.getClaveJuzgado(), j.getNombre(), j.getApellido(), j.getAnosServicio());
        if (j.getJuicios() != null) {
            response.setCantidadJuicios(j.getJuicios().size());
            List<JuicioResponse> listaJuiciosDto = j.getJuicios().stream().map(juicio -> {
                JuicioResponse jr = new JuicioResponse();
                jr.setId(juicio.getId());
                jr.setExpediente(juicio.getExpediente());
                jr.setFechaJuicio(juicio.getFechaJuicio());
                jr.setCondenado(juicio.isCondenado());
                if (juicio.getPersonaDetenida() != null) {
                    var p = juicio.getPersonaDetenida();
                    jr.setPersona(new PersonaDetenidaResponse(p.getId(), p.getCodigo(), p.getNombre(), p.getApellido(), null, null));
                }
                return jr;
            }).collect(Collectors.toList());
            response.setJuicios(listaJuiciosDto);
        } else {
            response.setCantidadJuicios(0);
        }
        return response;
    }

    public Juez toEntity(JuezRequest req) {
        Juez j = new Juez();
        j.setClaveJuzgado(req.getClaveJuzgado());
        j.setNombre(req.getNombre());
        j.setApellido(req.getApellido());
        j.setAnosServicio(req.getAnosServicio());
        return j;
    }
}