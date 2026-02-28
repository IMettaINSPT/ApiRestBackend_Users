package com.tp.backend.contrato.infrastructure;

import com.tp.backend.contrato.domain.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    boolean existsByVigilanteIdAndSucursalIdAndFechaContrato(Long vId, Long sId, LocalDate f);
    List<Contrato> findBySucursalId(Long sId);
    List<Contrato> findByVigilanteId(Long vId);
}