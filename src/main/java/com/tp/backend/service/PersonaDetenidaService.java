package com.tp.backend.service;

import com.tp.backend.dto.personaDetenida.*;
import com.tp.backend.exception.BadRequestException;
import com.tp.backend.exception.NotFoundException;
import com.tp.backend.model.Banda;
import com.tp.backend.model.PersonaDetenida;
import com.tp.backend.repository.BandaRepository;
import com.tp.backend.repository.PersonaDetenidaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonaDetenidaService {

    private final PersonaDetenidaRepository repo;
    private final BandaRepository bandaRepo;

    public PersonaDetenidaService(PersonaDetenidaRepository repo, BandaRepository bandaRepo) {
        this.repo = repo;
        this.bandaRepo = bandaRepo;
    }

    @Transactional(readOnly = true)
    public List<PersonaDetenidaResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public PersonaDetenidaResponse obtener(Long id) {
        PersonaDetenida p = repo.findById(id).orElseThrow(() -> new NotFoundException("PersonaDetenida no encontrada: " + id));
        return toResponse(p);
    }

    @Transactional
    public PersonaDetenidaResponse crear(PersonaDetenidaRequest req) {
        if (repo.existsByCodigo(req.getcodigo())) {
            throw new BadRequestException("Ya existe una persona detenida con el codigo: " + req.getcodigo());
        }
        PersonaDetenida p = new PersonaDetenida();
        p.setNombre(req.getNombre());
        p.setCodigo(req.getcodigo());
        p.setBanda(resolveBandaOrNull(req.getBandaId()));
        return toResponse(repo.save(p));
    }

    @Transactional
    public PersonaDetenidaResponse actualizar(Long id, PersonaDetenidaUpdateRequest req) {
        PersonaDetenida p = repo.findById(id).orElseThrow(() -> new NotFoundException("PersonaDetenida no encontrada: " + id));


        p.setNombre(req.getNombre());
        p.setBanda(resolveBandaOrNull(req.getBandaId()));
        return toResponse(p);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("PersonaDetenida no encontrada: " + id);
        repo.deleteById(id);
    }

    private Banda resolveBandaOrNull(Long bandaId) {
        if (bandaId == null) return null;
        return bandaRepo.findById(bandaId).orElseThrow(() -> new NotFoundException("Banda no encontrada: " + bandaId));
    }

    private PersonaDetenidaResponse toResponse(PersonaDetenida p) {
        Long bandaId = p.getBanda() != null ? p.getBanda().getId() : null;
        return new PersonaDetenidaResponse(p.getId(), p.getNombre(), bandaId, p.getCodigo());
    }
}
