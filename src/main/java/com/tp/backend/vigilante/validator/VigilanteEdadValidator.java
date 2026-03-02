package com.tp.backend.vigilante.validator;

import com.tp.backend.vigilante.domain.VigilanteValidator;
import com.tp.backend.vigilante.dto.VigilanteUpdateRequest;
import com.tp.backend.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class VigilanteEdadValidator implements VigilanteValidator<VigilanteUpdateRequest> {
    @Override
    public void validar(VigilanteUpdateRequest req) {
        if (req.getEdad() < 18) {
            throw new BadRequestException("El vigilante debe ser mayor de edad");
        }
    }
}