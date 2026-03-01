package com.tp.backend.banda.application;

import com.tp.backend.banda.dto.*;
import java.util.List;

public interface BandaUseCase {
    List<BandaResponse> listar();
    BandaResponse obtener(Long id);
    BandaResponse crear(BandaRequest req);
    BandaResponse actualizar(Long id, BandaUpdateRequest req);
    void eliminar(Long id);
}