package com.tp.backend.personaDetenida.domain;

import java.util.List;
import java.util.Optional;

public interface PersonaDetenidaPort {
    List<PersonaDetenida> listar();
    Optional<PersonaDetenida> obtener(Long id);
    PersonaDetenida guardar(PersonaDetenida persona);
    void eliminar(Long id);
    boolean existsByCodigo(String codigo);
    boolean existsById(Long id);
}