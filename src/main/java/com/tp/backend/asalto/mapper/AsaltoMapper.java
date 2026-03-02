package com.tp.backend.asalto.mapper;

import com.tp.backend.asalto.domain.Asalto;
import com.tp.backend.asalto.dto.*;
import com.tp.backend.sucursal.dto.SucursalResponse;
import com.tp.backend.model.Sucursal;
import com.tp.backend.personaDetenida.domain.PersonaDetenida;
import com.tp.backend.personaDetenida.dto.PersonaDetenidaResponse;
import com.tp.backend.banda.dto.BandaResponse;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AsaltoMapper {
    public AsaltoResponse toResponse(Asalto a) {
        AsaltoResponse r = new AsaltoResponse();
        r.setId(a.getId());
        r.setCodigo(a.getCodigo());
        r.setFechaAsalto(a.getFechaAsalto());

        if (a.getSucursal() != null) {
            Sucursal s = a.getSucursal();
            r.setSucursal(new SucursalResponse(s.getId(), s.getCodigo(), s.getDomicilio(), s.getNroEmpleados(),
                    s.getBanco() != null ? s.getBanco().getId() : null, s.getBanco() != null ? s.getBanco().getCodigo() : null));
        }

        if (a.getPersonas() != null) {
            r.setPersonas(a.getPersonas().stream().map(p -> {
                BandaResponse bandaDTO = null;
                if (p.getBanda() != null) {
                    bandaDTO = new BandaResponse();
                    bandaDTO.setId(p.getBanda().getId());
                    bandaDTO.setNumeroBanda(p.getBanda().getNumeroBanda());
                }
                return new PersonaDetenidaResponse(p.getId(), p.getCodigo(), p.getNombre(), p.getApellido(), bandaDTO, null);
            }).toList());
        }
        return r;
    }

    public Asalto toEntity(AsaltoRequest req, Sucursal s, List<PersonaDetenida> p) {
        Asalto a = new Asalto();
        a.setCodigo(req.getCodigo());
        a.setFechaAsalto(req.getFechaAsalto());
        a.setSucursal(s);
        a.setPersonas(p);
        return a;
    }
}
