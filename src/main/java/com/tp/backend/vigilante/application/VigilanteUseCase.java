package com.tp.backend.vigilante.application;

import com.tp.backend.vigilante.dto.*;
import java.util.List;

public interface VigilanteUseCase {
    List<VigilanteResponse> listar();
    VigilanteResponse obtener(Long id);
    VigilanteResponse crear(VigilanteRequest req);
    VigilanteResponse actualizar(Long id, VigilanteUpdateRequest req);
    void eliminar(Long id);
    List<VigilanteResponse> listarDisponibles();
    long countDisponibles();
    VigilanteResponse obtenerMiPerfil(String username);
}