package com.tp.backend.contrato.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ContratoPort {
    List<Contrato> listar();
    Optional<Contrato> obtener(Long id);
    Contrato guardar(Contrato contrato);
    void eliminar(Long id);
    boolean existsByVigilanteIdAndSucursalIdAndFechaContrato(Long vId, Long sId, LocalDate fecha);
    List<Contrato> findBySucursalId(Long sucursalId);
    List<Contrato> findByVigilanteId(Long vigilanteId);
    boolean existsById(Long id);
}
