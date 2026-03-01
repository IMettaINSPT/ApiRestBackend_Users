package com.tp.backend.juez.application;

import com.tp.backend.juez.dto.*;
import java.util.List;

public interface JuezUseCase {
    List<JuezResponse> listar();
    JuezResponse obtener(Long id);
    JuezResponse crear(JuezRequest req);
    JuezResponse actualizar(Long id, JuezUpdateRequest req);
    void eliminar(Long id);
}