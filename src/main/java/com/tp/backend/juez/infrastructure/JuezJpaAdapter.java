package com.tp.backend.juez.infrastructure;

import com.tp.backend.juez.domain.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class JuezJpaAdapter implements JuezPort {
    private final JuezRepository repo;

    public JuezJpaAdapter(JuezRepository repo) { this.repo = repo; }

    @Override public List<Juez> listar() { return repo.findAll(); }
    @Override public Optional<Juez> obtener(Long id) { return repo.findById(id); }
    @Override public Juez guardar(Juez j) { return repo.save(j); }
    @Override public void eliminar(Long id) { repo.deleteById(id); }
    @Override public boolean existsByClaveJuzgado(String c) { return repo.existsByClaveJuzgado(c); }
    @Override public boolean existsById(Long id) { return repo.existsById(id); }
}