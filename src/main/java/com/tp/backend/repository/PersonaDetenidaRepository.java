package com.tp.backend.repository;

import com.tp.backend.model.PersonaDetenida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaDetenidaRepository extends JpaRepository<PersonaDetenida, Long> {
    boolean existsByDni(String dni);
}
