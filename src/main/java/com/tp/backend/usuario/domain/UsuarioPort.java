package com.tp.backend.usuario.domain;

import java.util.List;
import java.util.Optional;

public interface UsuarioPort {
    List<Usuario> listar();
    Optional<Usuario> obtenerPorId(Long id);
    Optional<Usuario> obtenerPorUsername(String username);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    boolean existsByUsername(String username);
}