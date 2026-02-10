package com.tp.backend.repository;

import com.tp.backend.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    @Query("""
        select count(c) > 0
        from Contrato c
        where c.sucursal.id = :sucursalId
          and (c.fechaFin is null or c.fechaFin > :hoy)
    """)
    boolean existsActivoBySucursal(@Param("sucursalId") Long sucursalId,
                                   @Param("hoy") LocalDate hoy);

    @Query("""
        select count(c)
        from Contrato c
        where c.sucursal.id = :sucursalId
          and (c.fechaFin is null or c.fechaFin > :hoy)
    """)
    long countActivosBySucursal(@Param("sucursalId") Long sucursalId,
                                @Param("hoy") LocalDate hoy);
}
