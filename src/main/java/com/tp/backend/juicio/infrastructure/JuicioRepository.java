package com.tp.backend.juicio.infrastructure;

import com.tp.backend.juicio.domain.Juicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JuicioRepository extends JpaRepository<Juicio, Long> {
    boolean existsByExpediente(String expediente);
}
