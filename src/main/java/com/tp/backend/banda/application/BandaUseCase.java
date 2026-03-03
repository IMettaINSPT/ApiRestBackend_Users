package com.tp.backend.banda.application;

import com.tp.backend.banda.dto.*;
import com.tp.backend.common.application.ICreateUseCase;
import com.tp.backend.common.application.IDeleteUseCase;
import com.tp.backend.common.application.IReadUseCase;
import com.tp.backend.common.application.IUpdateUseCase;
import com.tp.backend.juez.dto.JuezRequest;
import com.tp.backend.juez.dto.JuezResponse;
import com.tp.backend.juez.dto.JuezUpdateRequest;

import java.util.List;

public interface BandaUseCase
        extends IReadUseCase<BandaResponse>,
        ICreateUseCase<BandaResponse, BandaRequest>,
        IUpdateUseCase<BandaResponse, BandaUpdateRequest>,
        IDeleteUseCase
{
    List<BandaResponse> listar();
    BandaResponse obtener(Long id);
    BandaResponse crear(BandaRequest req);
    BandaResponse actualizar(Long id, BandaUpdateRequest req);
    void eliminar(Long id);
}