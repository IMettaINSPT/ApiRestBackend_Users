package com.tp.backend.asalto.application;

import com.tp.backend.asalto.dto.*;
import com.tp.backend.banda.dto.BandaRequest;
import com.tp.backend.banda.dto.BandaResponse;
import com.tp.backend.banda.dto.BandaUpdateRequest;
import com.tp.backend.common.application.ICreateUseCase;
import com.tp.backend.common.application.IDeleteUseCase;
import com.tp.backend.common.application.IReadUseCase;
import com.tp.backend.common.application.IUpdateUseCase;
import com.tp.backend.personaDetenida.dto.PersonaDetenidaResponse;
import java.time.LocalDate;
import java.util.List;

public interface AsaltoUseCase
        extends
        ICreateUseCase<AsaltoResponse, AsaltoRequest>,
        IUpdateUseCase<AsaltoResponse, AsaltoRequest>,
        IDeleteUseCase
{
    List<AsaltoResponse> listarConFiltros(Long sucursalId, LocalDate fecha, LocalDate desde, LocalDate hasta);
    AsaltoResponse buscarPorId(Long id);
    AsaltoResponse crear(AsaltoRequest req);
    AsaltoResponse actualizar(Long id, AsaltoRequest req);
    void eliminar(Long id);
    List<PersonaDetenidaResponse> listarPersonasPorAsalto(Long id);
}