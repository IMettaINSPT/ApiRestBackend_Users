package com.tp.backend.banco.application;

import com.tp.backend.common.application.*;
import com.tp.backend.banco.dto.*;
import java.util.List;

public interface BancoUseCase
        extends IReadUseCase<BancoResponse>,
        ICreateUseCase<BancoResponse, BancoRequest>,
        IUpdateUseCase<BancoResponse, BancoRequest>, // Usamos Request para Update como en tu contrato
        IDeleteUseCase
{
    List<BancoResponse> listar();
    BancoResponse obtener(Long id);
    BancoResponse crear(BancoRequest req);
    BancoResponse actualizar(Long id, BancoRequest req);
    void eliminar(Long id);
}