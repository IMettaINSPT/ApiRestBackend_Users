package com.tp.backend.asalto.infrastructure;

import com.tp.backend.asalto.domain.Asalto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface AsaltoRepository extends JpaRepository<Asalto, Long> {
    List<Asalto> findByPersonas_Id(Long personaDetenidaId);

    @Query("SELECT DISTINCT a FROM Asalto a " +
            "LEFT JOIN FETCH a.personas " +
            "JOIN FETCH a.sucursal " +
            "WHERE (:sucursalId IS NULL OR a.sucursal.id = :sucursalId) " +
            "AND (:fecha IS NULL OR a.fechaAsalto = :fecha) " +
            "AND (:desde IS NULL OR a.fechaAsalto >= :desde) " +
            "AND (:hasta IS NULL OR a.fechaAsalto <= :hasta)")
    List<Asalto> filtrar(
            @Param("sucursalId") Long sucursalId,
            @Param("fecha") LocalDate fecha,
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta);
}