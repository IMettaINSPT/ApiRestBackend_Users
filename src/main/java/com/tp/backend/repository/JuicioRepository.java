package com.tp.backend.repository;

import com.tp.backend.model.Juicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JuicioRepository extends JpaRepository<Juicio, Long> {

    List<Juicio> findByJuezId(Long juezId);
    List<Juicio> findByPersonaDetenidaId(Long personaDetenidaId);

    // Nuevo método para validar si el expediente ya existe
    boolean existsByExpediente(String expediente);
}