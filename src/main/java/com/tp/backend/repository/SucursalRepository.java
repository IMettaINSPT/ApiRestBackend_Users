package com.tp.backend.repository;

import com.tp.backend.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
    boolean existsByCodigo(String codigo);
}
