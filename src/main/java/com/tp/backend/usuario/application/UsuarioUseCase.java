package com.tp.backend.usuario.application;

import com.tp.backend.common.application.*;
import com.tp.backend.usuario.dto.*;
import java.util.List;

public interface UsuarioUseCase
        extends IReadUseCase<UsuarioResponse>,
        ICreateUseCase<UsuarioResponse, UsuarioRequest>,
        IUpdateUseCase<UsuarioResponse, UsuarioUpdateRequest>,
        IDeleteUseCase
{
    UsuarioResponse obtenerPerfilAutenticado();
}