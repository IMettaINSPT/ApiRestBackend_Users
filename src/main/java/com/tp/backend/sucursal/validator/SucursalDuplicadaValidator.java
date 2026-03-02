package com.tp.backend.sucursal.validator;

import com.tp.backend.sucursal.domain.*;
import com.tp.backend.sucursal.dto.SucursalRequest;
import com.tp.backend.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class SucursalDuplicadaValidator implements SucursalValidator<SucursalRequest> {
    private final SucursalPort port;
    public SucursalDuplicadaValidator(SucursalPort port) { this.port = port; }

    @Override
    public void validar(SucursalRequest req) {
        if (port.existsByCodigo(req.getCodigo())) {
            throw new BadRequestException("Ya existe una sucursal con ese código");
        }
    }
}