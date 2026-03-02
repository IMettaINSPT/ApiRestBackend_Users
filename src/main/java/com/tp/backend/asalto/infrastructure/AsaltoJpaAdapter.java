package com.tp.backend.asalto.infrastructure;

import com.tp.backend.asalto.domain.*;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class AsaltoJpaAdapter implements AsaltoPort {
    private final AsaltoRepository repo;

    public AsaltoJpaAdapter(AsaltoRepository repo) { this.repo = repo; }

    @Override public List<Asalto> filtrar(Long sId, LocalDate f, LocalDate d, LocalDate h) { return repo.filtrar(sId, f, d, h); }
    @Override public Optional<Asalto> buscarPorId(Long id) { return repo.findById(id); }
    @Override public Asalto guardar(Asalto a) { return repo.save(a); }
    @Override public void eliminar(Long id) { repo.deleteById(id); }
    @Override public boolean existsById(Long id) { return repo.existsById(id); }
    @Override public List<Asalto> findByPersonas_Id(Long pId) { return repo.findByPersonas_Id(pId); }
}
