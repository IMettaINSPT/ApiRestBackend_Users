package com.tp.backend.repository;

import com.tp.backend.model.Vigilante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VigilanteRepository extends JpaRepository<Vigilante, Long> {
    boolean existsByCodigo(String codigo);
}
