package com.tp.backend.usuario.infrastructure;

import com.tp.backend.usuario.domain.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class UsuarioJpaAdapter implements UsuarioPort {
    private final UsuarioRepository repo;
    public UsuarioJpaAdapter(UsuarioRepository repo) { this.repo = repo; }

    @Override public List<Usuario> listar() { return repo.findAll(); }
    @Override public Optional<Usuario> obtenerPorId(Long id) { return repo.findById(id); }
    @Override public Optional<Usuario> obtenerPorUsername(String u) { return repo.findByUsername(u); }
    @Override public Usuario guardar(Usuario u) { return repo.save(u); }
    @Override public void eliminar(Long id) { repo.deleteById(id); }
    @Override public boolean existsByUsername(String u) { return repo.existsByUsername(u); }
}