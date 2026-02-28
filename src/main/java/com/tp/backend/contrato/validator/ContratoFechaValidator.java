package com.tp.backend.contrato.validator;

import com.tp.backend.contrato.domain.ContratoValidator;
import com.tp.backend.contrato.dto.ContratoRequest;
import com.tp.backend.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ContratoFechaValidator implements ContratoValidator {
    @Override
    public void validar(Object obj) {
        ContratoRequest req = (ContratoRequest) obj;
        if (req.getFechaFin() != null && req.getFechaFin().isBefore(req.getFechaContrato())) {
            throw new BadRequestException("La fecha de fin no puede ser anterior al inicio");
        }
    }
}