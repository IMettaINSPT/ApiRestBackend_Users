package com.tp.backend.repository;

import com.tp.backend.model.Asalto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AsaltoRepository extends JpaRepository<Asalto, Long> {

    List<Asalto> findBySucursal_Id(Long sucursalId);

    List<Asalto> findBySucursal_IdAndFechaAsalto(Long sucursalId, LocalDate fechaAsalto);

    List<Asalto> findBySucursal_IdAndFechaAsaltoBetween(Long sucursalId, LocalDate desde, LocalDate hasta);

    List<Asalto> findByPersonaDetenida_Id(Long personaDetenidaId);
}
