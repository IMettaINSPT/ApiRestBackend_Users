package com.tp.backend.banco.validator;

import com.tp.backend.banco.domain.*;
import com.tp.backend.banco.dto.BancoRequest;
import com.tp.backend.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class BancoCodigoValidator implements BancoValidator<BancoRequest> {
    private final BancoPort port;
    public BancoCodigoValidator(BancoPort port) { this.port = port; }

    @Override
    public void validar(BancoRequest req) {
        if (port.existsByCodigo(req.getCodigo())) {
            throw new BadRequestException("Ya existe un banco con ese código");
        }
    }
}