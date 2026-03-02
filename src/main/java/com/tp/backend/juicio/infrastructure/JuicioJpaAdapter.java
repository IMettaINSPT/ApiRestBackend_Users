package com.tp.backend.juicio.infrastructure;

import com.tp.backend.juicio.domain.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class JuicioJpaAdapter implements JuicioPort {
    private final JuicioRepository repo;
    public JuicioJpaAdapter(JuicioRepository repo) { this.repo = repo; }

    @Override public List<Juicio> listar() { return repo.findAll(); }
    @Override public Optional<Juicio> obtener(Long id) { return repo.findById(id); }
    @Override public Juicio guardar(Juicio j) { return repo.save(j); }
    @Override public void eliminar(Long id) { repo.deleteById(id); }
    @Override public boolean existsByExpediente(String e) { return repo.existsByExpediente(e); }
    @Override public boolean existsById(Long id) { return repo.existsById(id); }
}
