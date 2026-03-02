package com.tp.backend.banco.domain;

import java.util.List;
import java.util.Optional;

public interface BancoPort {
    List<Banco> listar();
    Optional<Banco> obtener(Long id);
    Banco guardar(Banco banco);
    void eliminar(Long id);
    boolean existsById(Long id);
    boolean existsByCodigo(String codigo);
}