package com.tp.backend.repository;

import com.tp.backend.model.Banda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandaRepository extends JpaRepository<Banda, Long> {
    boolean existsByNombre(String nombre);
}
