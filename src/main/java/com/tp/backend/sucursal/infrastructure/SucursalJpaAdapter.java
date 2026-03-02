package com.tp.backend.sucursal.infrastructure;

import com.tp.backend.sucursal.domain.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class SucursalJpaAdapter implements SucursalPort {
    private final SucursalRepository repo;
    public SucursalJpaAdapter(SucursalRepository repo) { this.repo = repo; }

    @Override public List<Sucursal> listar() { return repo.findAll(); }
    @Override public Optional<Sucursal> obtener(Long id) { return repo.findById(id); }
    @Override public Sucursal guardar(Sucursal s) { return repo.save(s); }
    @Override public void eliminar(Long id) { repo.deleteById(id); }
    @Override public boolean existsById(Long id) { return repo.existsById(id); }
    @Override public boolean existsByCodigo(String c) { return repo.existsByCodigo(c); }
    @Override public List<Sucursal> findByBancoId(Long id) { return repo.findByBancoId(id); }
}