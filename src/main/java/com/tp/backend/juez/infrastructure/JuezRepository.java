package com.tp.backend.juez.infrastructure;

import com.tp.backend.juez.domain.Juez;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JuezRepository extends JpaRepository<Juez, Long> {
    boolean existsByClaveJuzgado(String claveJuzgado);
}
