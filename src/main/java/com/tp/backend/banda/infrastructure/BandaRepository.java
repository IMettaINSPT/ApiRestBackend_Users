package com.tp.backend.banda.infrastructure;

import com.tp.backend.banda.domain.Banda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandaRepository extends JpaRepository<Banda, Long> {
    boolean existsByNumeroBanda(Integer numeroBanda);
}
