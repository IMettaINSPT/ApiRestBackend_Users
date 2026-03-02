package com.tp.backend.personaDetenida.validator;

import com.tp.backend.personaDetenida.domain.*;
import com.tp.backend.personaDetenida.dto.PersonaDetenidaRequest;
import com.tp.backend.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class PersonaDetenidaDuplicadaValidator implements PersonaDetenidaValidator<PersonaDetenidaRequest> {
    private final PersonaDetenidaPort port;
    public PersonaDetenidaDuplicadaValidator(PersonaDetenidaPort port) { this.port = port; }

    @Override
    public void validar(PersonaDetenidaRequest req) {
        if (port.existsByCodigo(req.getcodigo())) {
            throw new BadRequestException("Ya existe una persona con el código: " + req.getcodigo());
        }
    }
}
