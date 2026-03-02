package com.tp.backend.sucursal.infrastructure;

import com.tp.backend.sucursal.domain.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
    boolean existsByCodigo(String codigo);
    List<Sucursal> findByBancoId(Long bancoId);
}