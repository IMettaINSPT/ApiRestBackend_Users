package com.tp.backend.sucursal.application;

import com.tp.backend.common.application.*;
import com.tp.backend.sucursal.dto.*;
import java.util.List;

public interface SucursalUseCase
        extends IReadUseCase<SucursalResponse>,
        ICreateUseCase<SucursalResponse, SucursalRequest>,
        IUpdateUseCase<SucursalResponse, SucursalRequest>,
        IDeleteUseCase
{
    List<SucursalResponse> listar();
    SucursalResponse obtener(Long id);
    SucursalResponse crear(SucursalRequest req);
    SucursalResponse actualizar(Long id, SucursalRequest req);
    void eliminar(Long id);
    List<SucursalResponse> listarPorBanco(Long bancoId);
}