package com.tp.backend.vigilante.validator;

import com.tp.backend.vigilante.domain.*;
import com.tp.backend.vigilante.dto.VigilanteUpdateRequest;
import com.tp.backend.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class VigilanteCodigoDuplicadoValidator implements VigilanteValidator<VigilanteUpdateRequest> {
    private final VigilantePort port;

    public VigilanteCodigoDuplicadoValidator(VigilantePort port) { this.port = port; }

    @Override
    public void validar(VigilanteUpdateRequest req) {
        if (port.existsByCodigo(req.getCodigo())) {
            throw new BadRequestException("Ya existe un vigilante con código: " + req.getCodigo());
        }
    }
}