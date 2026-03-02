package com.tp.backend.juicio.validator;

import com.tp.backend.juicio.domain.JuicioValidator;
import com.tp.backend.juicio.dto.JuicioRequest;
import com.tp.backend.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class JuicioFechaValidator implements JuicioValidator<JuicioRequest> {
    @Override
    public void validar(JuicioRequest req) {
        if (req.isCondenado() && req.getFechaInicioCondena() != null) {
            if (req.getFechaInicioCondena().isBefore(req.getFechaJuicio())) {
                throw new BadRequestException("La condena no puede empezar antes del juicio");
            }
        }
    }
}