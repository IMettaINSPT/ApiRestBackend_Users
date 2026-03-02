package com.tp.backend.juicio.domain;

import java.util.List;
import java.util.Optional;

public interface JuicioPort {
    List<Juicio> listar();
    Optional<Juicio> obtener(Long id);
    Juicio guardar(Juicio juicio);
    void eliminar(Long id);
    boolean existsByExpediente(String expediente);
    boolean existsById(Long id);
}