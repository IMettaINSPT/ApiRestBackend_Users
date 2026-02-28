package com.tp.backend.contrato.infrastructure;

import com.tp.backend.contrato.domain.*;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class ContratoJpaAdapter implements ContratoPort {
    private final ContratoRepository repo;
    public ContratoJpaAdapter(ContratoRepository repo) { this.repo = repo; }

    @Override public List<Contrato> listar() { return repo.findAll(); }
    @Override public Optional<Contrato> obtener(Long id) { return repo.findById(id); }
    @Override public Contrato guardar(Contrato c) { return repo.save(c); }
    @Override public void eliminar(Long id) { repo.deleteById(id); }
    @Override public boolean existsById(Long id) { return repo.existsById(id); }
    @Override public boolean existsByVigilanteIdAndSucursalIdAndFechaContrato(Long v, Long s, LocalDate f) {
        return repo.existsByVigilanteIdAndSucursalIdAndFechaContrato(v, s, f);
    }
    @Override public List<Contrato> findBySucursalId(Long id) { return repo.findBySucursalId(id); }
    @Override public List<Contrato> findByVigilanteId(Long id) { return repo.findByVigilanteId(id); }
}
