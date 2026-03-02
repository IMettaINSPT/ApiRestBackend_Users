package com.tp.backend.vigilante.domain;

import java.util.List;
import java.util.Optional;

public interface VigilantePort {
    List<Vigilante> listar();
    Optional<Vigilante> obtener(Long id);
    Vigilante guardar(Vigilante vigilante);
    void eliminar(Long id);
    boolean existsById(Long id);
    boolean existsByCodigo(String codigo);
    Optional<Vigilante> findByCodigo(String codigo);
    List<Vigilante> findDisponibles();
}
