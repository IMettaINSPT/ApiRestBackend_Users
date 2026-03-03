package com.tp.backend.juez.application;

import com.tp.backend.common.application.ICreateUseCase;
import com.tp.backend.common.application.IDeleteUseCase;
import com.tp.backend.common.application.IReadUseCase;
import com.tp.backend.common.application.IUpdateUseCase;
import com.tp.backend.juez.dto.*;

import java.util.List;

public interface JuezUseCase
        extends IReadUseCase<JuezResponse>,
        ICreateUseCase<JuezResponse, JuezRequest>,
        IUpdateUseCase<JuezResponse, JuezUpdateRequest>,
        IDeleteUseCase

{
    List<JuezResponse> listar();
    JuezResponse obtener(Long id);
    JuezResponse crear(JuezRequest req);
    JuezResponse actualizar(Long id, JuezUpdateRequest req);
    void eliminar(Long id);
}