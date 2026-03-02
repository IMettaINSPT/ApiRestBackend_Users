package com.tp.backend.personaDetenida.application;

import com.tp.backend.personaDetenida.dto.*;
import java.util.List;

public interface PersonaDetenidaUseCase {
    List<PersonaDetenidaResponse> listar();
    PersonaDetenidaResponse obtener(Long id);
    PersonaDetenidaResponse crear(PersonaDetenidaRequest req);
    PersonaDetenidaResponse actualizar(Long id, PersonaDetenidaUpdateRequest req);
    void eliminar(Long id);
}
