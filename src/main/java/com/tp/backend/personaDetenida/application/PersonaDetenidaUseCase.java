package com.tp.backend.personaDetenida.application;

import com.tp.backend.common.application.ICreateUseCase;
import com.tp.backend.common.application.IDeleteUseCase;
import com.tp.backend.common.application.IReadUseCase;
import com.tp.backend.common.application.IUpdateUseCase;
import com.tp.backend.contrato.dto.ContratoRequest;
import com.tp.backend.contrato.dto.ContratoResponse;
import com.tp.backend.contrato.dto.ContratoUpdateRequest;
import com.tp.backend.personaDetenida.dto.*;
import java.util.List;

public interface PersonaDetenidaUseCase  extends IReadUseCase<PersonaDetenidaResponse>,
        ICreateUseCase<PersonaDetenidaResponse, PersonaDetenidaRequest>,
        IUpdateUseCase<PersonaDetenidaResponse, PersonaDetenidaUpdateRequest>,
        IDeleteUseCase {
    List<PersonaDetenidaResponse> listar();
    PersonaDetenidaResponse obtener(Long id);
    PersonaDetenidaResponse crear(PersonaDetenidaRequest req);
    PersonaDetenidaResponse actualizar(Long id, PersonaDetenidaUpdateRequest req);
    void eliminar(Long id);
}
