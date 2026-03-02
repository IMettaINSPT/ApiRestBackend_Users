package com.tp.backend.contrato.mapper;

import com.tp.backend.contrato.domain.Contrato;
import com.tp.backend.contrato.dto.ContratoRequest;
import com.tp.backend.contrato.dto.ContratoResponse;
import com.tp.backend.sucursal.domain.Sucursal;
import com.tp.backend.vigilante.domain.Vigilante;
import org.springframework.stereotype.Component;

@Component
public class ContratoMapper {
    public ContratoResponse toResponse(Contrato c) {
        return new ContratoResponse(
                c.getId(), c.getNumContrato(), c.getFechaContrato(), c.isConArma(),
                c.getSucursal().getId(), c.getSucursal().getCodigo(),
                c.getVigilante().getId(), c.getVigilante().getCodigo(),
                c.getFechaFin(), c.getSucursal().getDomicilio()
        );
    }
    public Contrato toEntity(ContratoRequest req, Sucursal s, Vigilante v) {
        Contrato c = new Contrato();
        c.setFechaContrato(req.getFechaContrato());
        c.setFechaFin(req.getFechaFin());
        c.setConArma(req.isConArma());
        c.setSucursal(s);
        c.setVigilante(v);
        return c;
    }
}
