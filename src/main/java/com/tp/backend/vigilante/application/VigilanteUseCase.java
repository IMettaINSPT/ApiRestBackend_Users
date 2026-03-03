package com.tp.backend.vigilante.application;

import com.tp.backend.common.application.ICreateUseCase;
import com.tp.backend.common.application.IDeleteUseCase;
import com.tp.backend.common.application.IReadUseCase;
import com.tp.backend.common.application.IUpdateUseCase;
import com.tp.backend.contrato.dto.ContratoRequest;
import com.tp.backend.contrato.dto.ContratoResponse;
import com.tp.backend.contrato.dto.ContratoUpdateRequest;
import com.tp.backend.vigilante.dto.*;
import java.util.List;

public interface VigilanteUseCase  extends IReadUseCase<VigilanteResponse>,
        ICreateUseCase<VigilanteResponse, VigilanteRequest>,
        IUpdateUseCase<VigilanteResponse, VigilanteUpdateRequest>,
        IDeleteUseCase
{
    List<VigilanteResponse> listar();
    VigilanteResponse obtener(Long id);
    VigilanteResponse crear(VigilanteRequest req);
    VigilanteResponse actualizar(Long id, VigilanteUpdateRequest req);
    void eliminar(Long id);
    List<VigilanteResponse> listarDisponibles();
    long countDisponibles();
    VigilanteResponse obtenerMiPerfil(String username);
}