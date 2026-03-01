package com.tp.backend.juez.domain;

import java.util.List;
import java.util.Optional;

public interface JuezPort {
    List<Juez> listar();
    Optional<Juez> obtener(Long id);
    Juez guardar(Juez juez);
    void eliminar(Long id);
    boolean existsByClaveJuzgado(String claveJuzgado);
    boolean existsById(Long id);
}