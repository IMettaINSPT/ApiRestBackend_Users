package com.tp.backend.sucursal.domain;

import java.util.List;
import java.util.Optional;

public interface SucursalPort {
    List<Sucursal> listar();
    Optional<Sucursal> obtener(Long id);
    Sucursal guardar(Sucursal sucursal);
    void eliminar(Long id);
    boolean existsById(Long id);
    boolean existsByCodigo(String codigo);
    List<Sucursal> findByBancoId(Long bancoId);
}