package com.tp.backend.repository;

import com.tp.backend.model.Juez;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JuezRepository extends JpaRepository<Juez, Long> {
    boolean existsByClaveJuzgado(String claveJuzgado);
}
