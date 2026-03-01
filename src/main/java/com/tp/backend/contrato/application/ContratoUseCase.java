package com.tp.backend.contrato.application;

import com.tp.backend.common.application.*;
import com.tp.backend.contrato.dto.*;
import java.util.List;

public interface ContratoUseCase
        extends IReadUseCase<ContratoResponse>,
        ICreateUseCase<ContratoResponse, ContratoRequest>,
        IUpdateUseCase<ContratoResponse, ContratoUpdateRequest>,
        IDeleteUseCase
{
    List<ContratoResponse> listar();
    ContratoResponse obtener(Long id);
    ContratoResponse crear(ContratoRequest req);
    ContratoResponse actualizar(Long id, ContratoUpdateRequest req);
    void eliminar(Long id);
    List<ContratoResponse> listarPorSucursal(Long sucursalId);
    List<ContratoResponse> listarPorVigilante(Long vigilanteId);
}
