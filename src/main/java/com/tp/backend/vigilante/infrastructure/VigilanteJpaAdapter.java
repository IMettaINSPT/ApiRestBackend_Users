package com.tp.backend.vigilante.infrastructure;

import com.tp.backend.vigilante.domain.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class VigilanteJpaAdapter implements VigilantePort {
    private final VigilanteRepository repo;

    public VigilanteJpaAdapter(VigilanteRepository repo) { this.repo = repo; }

    @Override public List<Vigilante> listar() { return repo.findAll(); }
    @Override public Optional<Vigilante> obtener(Long id) { return repo.findById(id); }
    @Override public Vigilante guardar(Vigilante v) { return repo.save(v); }
    @Override public void eliminar(Long id) { repo.deleteById(id); }
    @Override public boolean existsById(Long id) { return repo.existsById(id); }
    @Override public boolean existsByCodigo(String codigo) { return repo.existsByCodigo(codigo); }
    @Override public Optional<Vigilante> findByCodigo(String codigo) { return repo.findByCodigo(codigo); }
    @Override public List<Vigilante> findDisponibles() { return repo.findDisponibles(); }
}