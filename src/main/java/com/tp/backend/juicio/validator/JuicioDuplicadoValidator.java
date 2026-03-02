package com.tp.backend.juicio.validator;

import com.tp.backend.juicio.domain.*;
import com.tp.backend.juicio.dto.JuicioRequest;
import com.tp.backend.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class JuicioDuplicadoValidator implements JuicioValidator<JuicioRequest> {
    private final JuicioPort port;
    public JuicioDuplicadoValidator(JuicioPort port) { this.port = port; }

    @Override
    public void validar(JuicioRequest req) {
        if (port.existsByExpediente(req.getExpediente())) {
            throw new BadRequestException("Ya existe un juicio con el expediente: " + req.getExpediente());
        }
    }
}