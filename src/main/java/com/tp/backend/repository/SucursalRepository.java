package com.tp.backend.repository;

import com.tp.backend.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
    boolean existsByCodigo(String codigo);
 long countByBanco_id(long bancoid);
    // alternativa más precisa
    @Query("""
        select count(c) > 0
        from Contrato c
        where c.sucursal.id = :sucursalId
          and (c.fechaFin is null or c.fechaFin > :hoy)
    """)
    boolean existsContratoActivo(@Param("sucursalId") Long sucursalId,
                                 @Param("hoy") LocalDate hoy);
}
