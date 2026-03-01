package com.tp.backend.personaDetenida.infrastructure;

import com.tp.backend.personaDetenida.domain.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class PersonaDetenidaJpaAdapter implements PersonaDetenidaPort {
    private final PersonaDetenidaRepository repo;

    public PersonaDetenidaJpaAdapter(PersonaDetenidaRepository repo) { this.repo = repo; }

    @Override public List<PersonaDetenida> listar() { return repo.findAll(); }
    @Override public Optional<PersonaDetenida> obtener(Long id) { return repo.findById(id); }
    @Override public PersonaDetenida guardar(PersonaDetenida p) { return repo.save(p); }
    @Override public void eliminar(Long id) { repo.deleteById(id); }
    @Override public boolean existsByCodigo(String c) { return repo.existsByCodigo(c); }
    @Override public boolean existsById(Long id) { return repo.existsById(id); }
}
