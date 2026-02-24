package com.tp.backend.repository;

import com.tp.backend.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    boolean existsByVigilanteIdAndSucursalIdAndFechaContrato(
            Long vigilanteId,
            Long sucursalId,
            LocalDate fechaContrato
    );

    List<Contrato> findBySucursalId(Long sucursalId);
    List<Contrato> findByVigilanteId(Long vigilanteId);
}
