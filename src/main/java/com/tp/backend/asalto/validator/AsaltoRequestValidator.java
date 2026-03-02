package com.tp.backend.asalto.validator;

import com.tp.backend.asalto.domain.AsaltoValidator;
import com.tp.backend.asalto.dto.AsaltoUpdateRequest;
import com.tp.backend.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class AsaltoRequestValidator implements AsaltoValidator<AsaltoUpdateRequest> {
    @Override
    public void validar(AsaltoUpdateRequest req) {
        if (req == null) throw new BadRequestException("Body requerido");
        if (req.getFechaAsalto() == null) throw new BadRequestException("fechaAsalto es obligatoria");
        if (req.getSucursalId() == null) throw new BadRequestException("sucursalId es obligatorio");
        if (req.getPersonaDetenidaIds() == null || req.getPersonaDetenidaIds().isEmpty()) {
            throw new BadRequestException("Debe seleccionar al menos un detenido");
        }
    }
}