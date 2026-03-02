package com.tp.backend.asalto.application;

import com.tp.backend.asalto.dto.*;
import com.tp.backend.personaDetenida.dto.PersonaDetenidaResponse;
import java.time.LocalDate;
import java.util.List;

public interface AsaltoUseCase {
    List<AsaltoResponse> listarConFiltros(Long sucursalId, LocalDate fecha, LocalDate desde, LocalDate hasta);
    AsaltoResponse buscarPorId(Long id);
    AsaltoResponse crear(AsaltoRequest req);
    AsaltoResponse actualizar(Long id, AsaltoRequest req);
    void eliminar(Long id);
    List<PersonaDetenidaResponse> listarPersonasPorAsalto(Long id);
}