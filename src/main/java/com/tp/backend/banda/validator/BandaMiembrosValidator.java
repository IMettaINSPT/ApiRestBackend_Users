package com.tp.backend.banda.validator;

import com.tp.backend.banda.domain.BandaValidator;
import com.tp.backend.banda.dto.BandaRequest;
import com.tp.backend.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class BandaMiembrosValidator implements BandaValidator<BandaRequest> {
    @Override
    public void validar(BandaRequest req) {
        if (req.getNumeroMiembros() < 0) {
            throw new BadRequestException("El número de miembros no puede ser negativo");
        }
    }
}