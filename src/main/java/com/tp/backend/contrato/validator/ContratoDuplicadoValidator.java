package com.tp.backend.contrato.validator;

import com.tp.backend.contrato.domain.*;
import com.tp.backend.contrato.dto.ContratoRequest;
import com.tp.backend.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ContratoDuplicadoValidator implements ContratoValidator {
    private final ContratoPort port;
    public ContratoDuplicadoValidator(ContratoPort port) { this.port = port; }

    @Override
    public void validar(Object obj) {
        ContratoRequest req = (ContratoRequest) obj;
        if (port.existsByVigilanteIdAndSucursalIdAndFechaContrato(req.getVigilanteId(), req.getSucursalId(), req.getFechaContrato())) {
            throw new BadRequestException("Ya existe un contrato idéntico");
        }
    }
}
