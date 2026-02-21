package com.tp.backend.repository;

import com.tp.backend.model.Asalto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AsaltoRepository extends JpaRepository<Asalto, Long> {

    List<Asalto> findBySucursal_Id(Long sucursalId);

    List<Asalto> findBySucursal_IdAndFechaAsalto(Long sucursalId, LocalDate fechaAsalto);

    List<Asalto> findBySucursal_IdAndFechaAsaltoBetween(Long sucursalId, LocalDate desde, LocalDate hasta);

    // es ManyToMany, usamos "personas_Id" (nombre del atributo en la entidad + _Id)
    List<Asalto> findByPersonas_Id(Long personaDetenidaId);

    // Agrego un JOIN FETCH para que al listar asaltos traiga las personas
    // de una sola vez y no haga 500 consultas a la base (Problema N+1)
    @Query("SELECT DISTINCT a FROM Asalto a LEFT JOIN FETCH a.personas JOIN FETCH a.sucursal")
    List<Asalto> findAllWithPersonas();

    /**
     * Nuevo método para el reporte dinámico.
     * Permite filtrar por sucursal, fecha exacta o rango de fechas de forma opcional.
     */
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
            @Param("hasta") LocalDate hasta
    );
}