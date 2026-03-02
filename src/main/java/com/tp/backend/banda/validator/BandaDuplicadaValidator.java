package com.tp.backend.banda.validator;

import com.tp.backend.banda.domain.*;
import com.tp.backend.banda.dto.BandaRequest;
import com.tp.backend.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class BandaDuplicadaValidator implements BandaValidator<BandaRequest> {
    private final BandaPort port;

    public BandaDuplicadaValidator(BandaPort port) { this.port = port; }

    @Override
    public void validar(BandaRequest req) {
        if (port.existsByNumeroBanda(req.getNumeroBanda())) {
            throw new BadRequestException("Ya existe una banda con este número: " + req.getNumeroBanda());
        }
    }
}
