package com.tp.backend.repository;

import com.tp.backend.model.PersonaDetenida;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonaDetenidaRepository extends JpaRepository<PersonaDetenida, Long> {
    boolean existsByCodigo(String codigo);

    @EntityGraph(attributePaths = "banda")
    Optional<PersonaDetenida> findById(Long id);

    @EntityGraph(attributePaths = "banda")
    List<PersonaDetenida> findAll();
}
