package com.tp.backend.banco.infrastructure;

import com.tp.backend.banco.domain.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class BancoJpaAdapter implements BancoPort {
    private final BancoRepository repo;

    public BancoJpaAdapter(BancoRepository repo) { this.repo = repo; }

    @Override public List<Banco> listar() { return repo.findAll(); }
    @Override public Optional<Banco> obtener(Long id) { return repo.findById(id); }
    @Override public Banco guardar(Banco b) { return repo.save(b); }
    @Override public void eliminar(Long id) { repo.deleteById(id); }
    @Override public boolean existsById(Long id) { return repo.existsById(id); }
    @Override public boolean existsByCodigo(String c) { return repo.existsByCodigo(c); }
}