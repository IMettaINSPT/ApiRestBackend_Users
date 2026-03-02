package com.tp.backend.juez.validator;

import com.tp.backend.juez.domain.*;
import com.tp.backend.juez.dto.JuezRequest;
import com.tp.backend.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class JuezDuplicadoValidator implements JuezValidator<JuezRequest> {
    private final JuezPort port;
    public JuezDuplicadoValidator(JuezPort port) { this.port = port; }

    @Override
    public void validar(JuezRequest req) {
        if (port.existsByClaveJuzgado(req.getClaveJuzgado())) {
            throw new BadRequestException("Ya existe un juez con código: " + req.getClaveJuzgado());
        }
    }
}