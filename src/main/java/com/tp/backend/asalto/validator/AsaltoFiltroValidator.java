package com.tp.backend.asalto.validator;

import com.tp.backend.asalto.domain.AsaltoValidator;
import com.tp.backend.asalto.dto.AsaltoUpdateRequest;
import com.tp.backend.exception.BadRequestException;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class AsaltoFiltroValidator {
    public void validar(LocalDate fecha, LocalDate desde, LocalDate hasta) {
        if (fecha != null && (desde != null || hasta != null)) {
            throw new BadRequestException("No se puede usar 'fecha exacta' junto con un período 'desde/hasta'");
        }
        if (desde != null && hasta != null && hasta.isBefore(desde)) {
            throw new BadRequestException("Rango de fechas inválido");
        }
    }
}