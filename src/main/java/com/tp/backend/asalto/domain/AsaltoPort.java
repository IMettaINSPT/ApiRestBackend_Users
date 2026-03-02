package com.tp.backend.asalto.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AsaltoPort {
    List<Asalto> filtrar(Long sucursalId, LocalDate fecha, LocalDate desde, LocalDate hasta);
    Optional<Asalto> buscarPorId(Long id);
    Asalto guardar(Asalto asalto);
    void eliminar(Long id);
    boolean existsById(Long id);
    List<Asalto> findByPersonas_Id(Long personaDetenidaId);
}