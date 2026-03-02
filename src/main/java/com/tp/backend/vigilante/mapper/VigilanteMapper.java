package com.tp.backend.vigilante.mapper;

import com.tp.backend.vigilante.domain.Vigilante;
import com.tp.backend.vigilante.dto.*;
import com.tp.backend.contrato.dto.ContratoResponse;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class VigilanteMapper {
    public VigilanteResponse toResponse(Vigilante v) {
        VigilanteResponse res = new VigilanteResponse(v.getId(), v.getCodigo(), v.getEdad());
        if (v.getContratos() != null) {
            res.setContratos(v.getContratos().stream()
                    .map(c -> new ContratoResponse(
                            c.getId(), c.getNumContrato(), c.getFechaContrato(), c.isConArma(),
                            c.getSucursal().getId(), c.getSucursal().getCodigo(),
                            v.getId(), v.getCodigo(), c.getFechaFin(), c.getSucursal().getDomicilio()
                    )).collect(Collectors.toList()));
        }
        return res;
    }

    public Vigilante toEntity(VigilanteRequest req) {
        Vigilante v = new Vigilante();
        v.setCodigo(req.getCodigo());
        v.setEdad(req.getEdad());
        return v;
    }
}
