package com.tp.backend.personaDetenida.infrastructure;

import com.tp.backend.personaDetenida.domain.PersonaDetenida;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PersonaDetenidaRepository extends JpaRepository<PersonaDetenida, Long> {
    boolean existsByCodigo(String codigo);

    @EntityGraph(attributePaths = {"banda", "asaltos"})
    Optional<PersonaDetenida> findById(Long id);

    @EntityGraph(attributePaths = {"banda", "asaltos"})
    List<PersonaDetenida> findAll();
}