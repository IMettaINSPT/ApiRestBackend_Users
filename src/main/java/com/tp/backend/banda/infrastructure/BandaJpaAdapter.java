package com.tp.backend.banda.infrastructure;

import com.tp.backend.banda.domain.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class BandaJpaAdapter implements BandaPort {
    private final BandaRepository repo;

    public BandaJpaAdapter(BandaRepository repo) { this.repo = repo; }

    @Override public List<Banda> listar() { return repo.findAll(); }
    @Override public Optional<Banda> obtener(Long id) { return repo.findById(id); }
    @Override public Banda guardar(Banda b) { return repo.save(b); }
    @Override public void eliminar(Long id) { repo.deleteById(id); }
    @Override public boolean existsById(Long id) { return repo.existsById(id); }
    @Override public boolean existsByNumeroBanda(Integer n) { return repo.existsByNumeroBanda(n); }
}