package com.tp.backend.juicio.application;

import com.tp.backend.juicio.dto.*;
import java.util.List;

public interface JuicioUseCase {
    List<JuicioResponse> listar();
    JuicioResponse obtener(Long id);
    JuicioResponse crear(JuicioRequest req);
    JuicioResponse actualizar(Long id, JuicioUpdateRequest req);
    void eliminar(Long id);
}