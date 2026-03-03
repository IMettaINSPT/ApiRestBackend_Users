package com.tp.backend.juicio.application;

import com.tp.backend.common.application.ICreateUseCase;
import com.tp.backend.common.application.IDeleteUseCase;
import com.tp.backend.common.application.IReadUseCase;
import com.tp.backend.common.application.IUpdateUseCase;
import com.tp.backend.juicio.dto.*;
import com.tp.backend.personaDetenida.dto.PersonaDetenidaRequest;
import com.tp.backend.personaDetenida.dto.PersonaDetenidaResponse;
import com.tp.backend.personaDetenida.dto.PersonaDetenidaUpdateRequest;

import java.util.List;

public interface JuicioUseCase
        extends IReadUseCase<JuicioResponse>,
        ICreateUseCase<JuicioResponse, JuicioRequest>,
        IUpdateUseCase<JuicioResponse, JuicioUpdateRequest>,
        IDeleteUseCase
{
    List<JuicioResponse> listar();
    JuicioResponse obtener(Long id);
    JuicioResponse crear(JuicioRequest req);
    JuicioResponse actualizar(Long id, JuicioUpdateRequest req);
    void eliminar(Long id);
}