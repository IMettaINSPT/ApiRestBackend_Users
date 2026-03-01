package com.tp.backend.personaDetenida.mapper;

import com.tp.backend.personaDetenida.domain.PersonaDetenida;
import com.tp.backend.personaDetenida.dto.*;
import com.tp.backend.banda.dto.BandaResponse;
import com.tp.backend.banda.domain.Banda;
import com.tp.backend.dto.asalto.AsaltoResponse;
import com.tp.backend.dto.sucursal.SucursalResponse;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PersonaDetenidaMapper {
    public PersonaDetenidaResponse toResponse(PersonaDetenida p) {
        BandaResponse bandaDto = (p.getBanda() == null) ? null :
                new BandaResponse(p.getBanda().getId(), p.getBanda().getNumeroBanda(), p.getBanda().getNumeroMiembros());

        List<AsaltoResponse> asaltosDto = (p.getAsaltos() == null) ? null :
                p.getAsaltos().stream().map(a -> {
                    AsaltoResponse res = new AsaltoResponse();
                    res.setId(a.getId());
                    res.setCodigo(a.getCodigo());
                    res.setFechaAsalto(a.getFechaAsalto());
                    if (a.getSucursal() != null) {
                        var s = a.getSucursal();
                        res.setSucursal(new SucursalResponse(s.getId(), s.getCodigo(), s.getDomicilio(), s.getNroEmpleados(),
                                s.getBanco() != null ? s.getBanco().getId() : null, s.getBanco() != null ? s.getBanco().getCodigo() : null));
                    }
                    return res;
                }).toList();

        return new PersonaDetenidaResponse(p.getId(), p.getCodigo(), p.getNombre(), p.getApellido(), bandaDto, asaltosDto);
    }

    public PersonaDetenida toEntity(PersonaDetenidaRequest req, Banda banda) {
        PersonaDetenida p = new PersonaDetenida();
        p.setCodigo(req.getcodigo());
        p.setNombre(req.getNombre());
        p.setApellido(req.getApellido());
        p.setBanda(banda);
        return p;
    }
}
