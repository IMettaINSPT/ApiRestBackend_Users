package com.tp.backend.banda.domain;

import java.util.List;
import java.util.Optional;

public interface BandaPort {
    List<Banda> listar();
    Optional<Banda> obtener(Long id);
    Banda guardar(Banda banda);
    void eliminar(Long id);
    boolean existsById(Long id);
    boolean existsByNumeroBanda(Integer numeroBanda);
}
