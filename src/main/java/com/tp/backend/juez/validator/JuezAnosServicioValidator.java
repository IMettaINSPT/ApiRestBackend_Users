package com.tp.backend.juez.validator;

import com.tp.backend.juez.domain.JuezValidator;
import com.tp.backend.juez.dto.JuezRequest;
import com.tp.backend.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class JuezAnosServicioValidator implements JuezValidator<JuezRequest> {
    @Override
    public void validar(JuezRequest req) {
        if (req.getAnosServicio() != null && req.getAnosServicio() < 0) {
            throw new BadRequestException("Los años de servicio no pueden ser negativos");
        }
    }
}
